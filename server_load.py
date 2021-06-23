#!/usr/bin/env python
# coding: utf-8

# In[6]:


import json
from elasticsearch import Elasticsearch
import pandas as pd
from datetime import datetime


# In[7]:

#please uncomment and place the path to your dataset/full dataset
#records=pd.read_csv("mini_dataset.csv")
records = records.drop(columns=['_id'])
es = Elasticsearch()
#display(records)
jobs = records.to_dict(orient="records")
# Send the data into es
order=1
if not es.indices.exists(index="dataset_jobs"):
    for job in jobs:
        job['date'] = job['date'][0:10]
        a = es.index(index='dataset_jobs', id=order, body=job)
        order = order+1


# In[ ]:





# In[ ]:




