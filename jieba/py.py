# coding: utf-8
'''
Created on 2015年9月5日

@author: FuShengXu
'''

import sys
from chaco.tests.serializable_base import Shape
reload(sys)
sys.setdefaultencoding( "utf-8" )
import jieba
import jieba.posseg as pseg
import csv
import os
import sys
from sklearn import feature_extraction
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.feature_extraction.text import CountVectorizer

csvfile=open('E:\TianChi\sentences.csv','r')
reader=csv.reader(csvfile)
dictCore={}
#中文网站里面其实也存在大量的stop word。比如,我们前面这句话,“在”、“里面”、“也”、“的”、“它”、“为”这些词都是停止词
stopwds=[",",'》',',','，','“','”','；','；',' ',",",',','、',';','。','【',',','.',';','+','-','|','/','\\','"','\'',':','?','<','>','[',']','{','}','!','@','#','$','%','^','&','*','(',')','~','`','　','，','。','；','‘','’','“','”','／','？','～','！','＠','＃','￥','％','……','＆','×','（','—','）','】','｛','｝','｜','、','《','》','：',"的", "我们","要","自己","之","将","后","应","到","某","后","个","是","位","新",
         "一","两","在","中","或","有","更","好","就","向","是"];
divideWordResult=list()  
for line in reader:
    
    divideWordResult.append(line[0])
    dictCore[line[0]]=0
csvfile.close()      



if __name__ == "__main__":
  corpus=divideWordResult
  vectorizer=CountVectorizer()#该类会将文本中的词语转换为词频矩阵，矩阵元素a[i][j] 表示j词在i类文本下的词频
  transformer=TfidfTransformer()#该类会统计每个词语的tf-idf权值
  tfidf=transformer.fit_transform(vectorizer.fit_transform(corpus))#第一个fit_transform是计算tf-idf，第二个fit_transform是将文本转为词频矩阵
  word=vectorizer.get_feature_names()#获取词袋模型中的所有词语
  weight=tfidf.toarray()#将tf-idf矩阵抽取出来，元素a[i][j]表示j词在i类文本中的tf-idf权重
  for i in range(len(weight)):#打印每类文本的tf-idf词语权重，第一个for遍历所有文本，第二个for便利某一类文本下的词语权重
    print u"-------这里输出第",i,u"类文本的词语tf-idf权重------"
    for j in range(len(word)):
      print word[j],weight[i][j]