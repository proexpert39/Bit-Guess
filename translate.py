import googletrans
from googletrans import Translator
from langdetect import detect
from pandas import read_csv
import csv

translator = Translator()
# open the file in the write mode
f = open('tweets_turkish/bitcoin_tweets_0.csv', 'a')
writer = csv.writer(f)
header = ["date","text"]
writer.writerow(header)


column_names = ["date","text"]
with open("tweets/bitcoin_tweets_0.csv", "r",encoding="utf-8") as csvfile:
    df = read_csv(csvfile,encoding="utf-8",engine='c',usecols=column_names,low_memory=False,sep=",")
    for index, row in df.iterrows():
        try:
            if( type(row.text) == str):
                tweet_text = row.text.strip()
                turkish_text = translator.translate(tweet_text,dest="tr").text.replace("\n","")  
                data = [row.date,turkish_text]
                writer.writerow(data)
        except Exception as e:
            print(e)
f.close()
