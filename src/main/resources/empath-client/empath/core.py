import os
import sys
from collections import defaultdict
from . import helpers as util
import requests
import json

class Empath:
    def __init__(self, backend_url="http://54.148.189.209:8000"):
        self.cats = defaultdict(list)
        self.staging = {}
        self.backend_url = backend_url
        self.base_dir = os.path.dirname(util.__file__)
        self.inv_cache = {}
        self.load(self.base_dir+"/data/categories.tsv")
        for f in os.listdir(self.base_dir+"/data/user/"):
            if len(f.split(".")) > 1 and f.split(".")[1] == "empath":
                self.load(self.base_dir+"/data/user/"+f)

    def load(self,file):
        with open(file,"r") as f:
            for line in f:
                cols = line.strip().split("\t")
                name = cols[0]
                terms = cols[1:]
                for t in set(terms):
                    self.cats[name].append(t)
                    #self.invcats[t].append(name)

    def analyze_term_window(self,doc,targets,categories=None,window_size=10,normalize=False):
        tokenizer = util.window_tokenizer(window_size,targets)
        return self.analyze(doc,categories,tokenizer,normalize)

    def analyze(self,doc,categories=None,tokenizer="default",normalize=False):
        if isinstance(doc,list):
            doc = "\n".join(doc)
        if tokenizer == "default":
            tokenizer = util.default_tokenizer
        elif tokenizer == "bigrams":
            tokenizer = util.bigram_tokenizer
        if not hasattr(tokenizer,"__call__"):
            raise Exception("invalid tokenizer")
        if not categories:
            categories = self.cats.keys()
        invcats = defaultdict(list)
        key = tuple(sorted(categories))
        if key in self.inv_cache:
            invcats = self.inv_cache[key]
        else:
            for k in categories:
                for t in self.cats[k]: invcats[t].append(k)
            self.inv_cache[key] = invcats
        count = {}
        tokens = 0.0
        for cat in categories: count[cat] = 0.0
        for tk in tokenizer(doc):
            tokens += 1.0
            for cat in invcats[tk]:
                count[cat]+=1.0
        if normalize:
            for cat in count.keys():
                if tokens == 0:
                    return None
                else:
                    count[cat] = count[cat] / tokens
        return count


    def create_category(self,name,seeds,model="fiction",size=100,write=True):
        resp = requests.post(self.backend_url + "/create_category", json={"terms":seeds,"size":size,"model":model})
        print(resp.text)
        results = json.loads(resp.text)
        self.cats[name] = list(set(results))
        if write:
            with open(self.base_dir+"/data/user/"+name+".empath","w") as f:
                f.write("\t".join([name]+results))

    def delete_category(self,name):
        if name in self.cats: del self.cats[name]
        filename = self.base_dir+"/data/user/"+name+".empath"
        if os.path.isfile(filename):
            os.remove(filename)
