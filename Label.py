# coding: utf-8
'''
Created on 2015年9月14日

@author: FuShengXu
'''

import jieba
import csv
import sys
from math import sqrt
reload(sys)
sys.setdefaultencoding( "utf-8" )
rate1=1
rate2=5
rate3=7
rate4=3
label1={u"CPI":rate1}
label2={u"PPI":rate1}
label3={u"环比":rate1}
label4={u"同比":rate1}
label5={u"持平":rate3}
label6={u"反弹":rate1,u"回升":rate1,u"上涨":rate4,u"增长":rate4,u"涨幅":rate4,u"大涨":rate2,u"通胀":rate2,"通货膨胀":rate2,"反弹":rate2}
label7={u"下降":rate4,u"跌幅":rate4,u"回落":rate4,u"下跌":rate2,u"通缩":rate2,u"通货紧缩":rate2,"下探":rate2}
label8={u"工业":rate1,u"生产者":rate1,u"出厂":rate1,u"购进":rate1}
label9={u"食品":rate1}
label10={u"猪肉":rate1,u"鲜菜":rate1,u"水果":rate1,u"鸡蛋":rate1}
label11={u"石油":rate1,u"天然气":rate1,u"国际原油价格":rate1}
label12={u"化学原料":rate1,u"进出口价格":rate1}
label13={u"黑色金属":rate1,u"钢铁":rate1,u"主动降价":rate1,u"铁矿石":rate1,u"投资":rate1,u"需求":1}
label14={u"有色金属":rate1,u"电解铝":rate1,u"国际大宗商品":rate1}
label15={u"煤炭":rate1,u"季节性需求":rate1,u"产能调整去库存":rate1}
label16={u"产能调整":rate1,u"库存":rate1}
label17={u"水泥":rate1,u"投资需求":rate1}
'''
label9={u"猪肉":rate1,u"生产":rate1,u"资料":rate1,u"原":rate1,u"材料":rate1,u"加工":rate1,u"生活":rate1,u"食品":rate1,u"日用品":rate1,u"耐用":rate1,u"消费":rate1
                 ,u"燃料":rate1,u"动力":rate1,u"黑色":rate1,u"金属":rate1,u"化工":rate1,u"有色":rate1,u"电线":rate1,u"木材":rate1,u"纸浆":rate1,u"建筑":rate1,u"工业":rate1
                 ,u"农":rate1,u"副":rate1,u"纺织":rate1,u"煤炭":rate1,u"洗":rate1,u"选":rate1,u"石油":rate1,u"天然气":rate1}
'''                 
Resource=[]
Resource.append(label1)
Resource.append(label2)
Resource.append(label3)
Resource.append(label4)
Resource.append(label5)
Resource.append(label6)
Resource.append(label7)
Resource.append(label8)
Resource.append(label9)
Resource.append(label10)
Resource.append(label11)
Resource.append(label12)
Resource.append(label13)
Resource.append(label14)
Resource.append(label15)
Resource.append(label16)
Resource.append(label17)
def generateLineNew(Resouce,base):
    temp=[]
    number=17
    while number>0:
        temp.append(0)
        number-=1
    for baseline in base:
        i=0
        for resourceline in Resource:
            if resourceline.has_key(baseline):
                temp[i]+=resourceline[baseline]
                break
            i+=1  
    return temp     
def generateLine(Resouce,base):
    temp=[]
    number=9
    while number>0:
        temp.append(0)
        number-=1
    for baseline in base:
        i=0
        for resourceline in Resource:
            if baseline in resourceline:
                temp[i]+=1
                break
            i+=1  
    return temp 

def countSimiDistance(line1,line2):
    temp=[x-y for x,y in zip(line1,line2)]
    temp1=[x*x for x in temp]
    distance=sum(temp1)
    return distance        
def countCos(line1,line2): 
    temp1=[x*x for x in line1]
    temp2=[x*x for x in line2]
    sum1=sum(temp1)
    sum2=sum(temp2)
    sqr1=sqrt(sum1)
    sqr2=sqrt(sum2)
    temp12=sum([x*y for x,y in zip(line1,line2)])
    cos=0
    if sqr1==0 or sqr2==0:
        cos=0
    else:
        cos=temp12/sqr1/sqr2
   # cosSim=0.5+0.5*cos
    return cos
             

def countAllDistance(Resource,base,CPIPPI):
    baseTemp=generateLineNew(Resource,base[0])
    dictTemp={}
    i=0
    for line in CPIPPI:
        temp=generateLineNew(Resource, line)
        number=countCos(temp, baseTemp)
        dictTemp[i]=number
        i+=1  
    return dictTemp        

#divideWordResult存放分词结果,并保存到divideWordResult.csv文件中
def loadFile(filename):
    temp=[]
    csvfile=open(filename,'r')
    reader=csv.reader(csvfile)
    for line in reader:
        temp.append(line[0])
    return temp    
def chooseBestFit(listT,dictT,number):
    i=0
    dictResult=sorted(dictT.iteritems(), key=lambda dictT:dictT[1],reverse=True)
    temp=list()
    for key in dictResult:
        if i==number:
            break
        i+=1   
        temp.append([listT[key[0]]])
        print key[1]
    return temp    
      
def returnDivide(filename):
    csvfile=open(filename,'r')
    reader=csv.reader(csvfile)
    #中文网站里面其实也存在大量的stop word。比如,我们前面这句话,“在”、“里面”、“也”、“的”、“它”、“为”这些词都是停止词
    stopwds=[u",",u'》',u'，',u"\xa3\xac",u'“',u'”',u'；',u'；',u' ',u",",u',','、u',';',u'。','【',',','.',';','+','-','|','/','\\','"','\'',':','?','<','>','[',']','{','}','!','@','#','$','%','^','&','*','(',')','~','`','　','，','。','；','‘','’','“','”','／','？','～','！','＠','＃','￥','％','……','＆','×','（','—','）','】','｛','｝','｜','、','《','》','：',"的", "我们","要","自己","之","将","后","应","到","某","后","个","是","位","新",
         u"一",u"两",u"在",u"中",u"或",u"有",u"更",u"好",u"就",u"向",u"是"];
    divideWordResult=list()  
    for line in reader:
        divideWordResult.append(list(jieba.cut(line[0],cut_all=False)))
    csvfile.close() 
    divideWordResult1=[]        
    for line in divideWordResult:
        temp=[]
        for key in line:
            if unicode(key) not in stopwds:
                #line.remove(key)
                temp.append(key)
        divideWordResult1.append(temp)     
    return divideWordResult1
def printtofile(dataset,filename):
    wf3=open(filename,'wb')
    writer3=csv.writer(wf3)
    writer3.writerows(dataset)
    wf3.close()
def coutSimilar(dict1,dict2):
    count=0
    temp=[]
    for line in dict1:
        if line in dict2:
            temp.append(line)
            count+=1      
    return count,temp 
def countSimiNumber(divideBase,divideCPIPPI):
    SimilarList=[]
    for line in divideCPIPPI:
        number,temp=coutSimilar(dividebase[0],line)
        SimilarList.append(temp)
        #print number 
    return SimilarList    
filename1='E:\TianChi\CPImaster.csv'
filename2='E:\TianChi\yuqiumei.csv'
filename3='E:\TianChi\gaaaa.csv'
filename4='E:\TianChi\gbbbb.csv'
filename6='E:\TianChi\gFinalResult.csv'
listYushi=loadFile(filename2)


divideCPIPPI=returnDivide(filename2)
dividebase=returnDivide(filename1)
SimilarList=countSimiNumber(dividebase,divideCPIPPI)
dictYushi=countAllDistance(Resource, dividebase,divideCPIPPI)
dictShuchu=chooseBestFit(listYushi,dictYushi,50)
printtofile(dictShuchu, filename6)
'''
    for line in divideCPIPPI:
    number=0
    number,temp=coutSimilar(dividebase[0],line)
    SimilarList.append(temp)
    print number
'''    
printtofile(dividebase,filename3)
printtofile(divideCPIPPI,filename4)
filename5='E:\TianChi\gRepeat.csv'
printtofile(SimilarList,filename5)   