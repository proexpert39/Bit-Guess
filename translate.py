import googletrans
from googletrans import Translator
from langdetect import detect
from pandas import read_csv
import csv

translator = Translator()
# open the file in the write mode
f = open('tweets_turkish/bitcoin_tweets_2.csv', 'a',newline="")
writer = csv.writer(f)
header = ["date","text"]
writer.writerow(header)


column_names = ["date","text"]
with open("tweets/bitcoin_tweets_2.csv", "r",encoding="utf-8") as csvfile:
    df = read_csv(csvfile,encoding="utf-8",engine='python',usecols=column_names,sep=",",quoting=3)
    for index, row in df.iterrows():
        try:
            if( type(row.text) == str):
                tweet_text = row.text.strip()
                turkish_text = translator.translate(tweet_text,dest="tr").text.replace("\r","").replace("\n","")
                if(turkish_text):
                    data = [row.date,turkish_text]
                    writer.writerow(data)
        except Exception as e:
            print(e)
f.close()
