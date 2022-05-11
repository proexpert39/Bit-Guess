from googletrans import Translator
from pandas import read_csv
import csv
import dateutil.parser
from datetime import datetime
import re

def valid_date(s):
    try:
        s_datetime = datetime.strptime(s, '%Y-%m-%d %H:%M:%S')
        if(2018 <= s_datetime.year <= 2022):
            return True
        else:
            return False
    except ValueError:
        return False
        #msg = "Not a valid date: '{0}'.".format(s)
        #raise argparse.ArgumentTypeError(msg)
    return False

translator = Translator()
# open the file in the write mode
f = open('tweets/Bitcoin_tweets.csv', 'a',newline="")
writer = csv.writer(f)
header = ["date","text"]
writer.writerow(header)


column_names = ["date","text"]
with open("tweets/bitcoin_tweets_turkish.csv", "r",encoding="utf-8") as csvfile:
    df = read_csv(csvfile,encoding="utf-8",engine='python',usecols=column_names,sep=",",quoting=3)
    for index, row in df.iterrows():
        try:
            if(row.date and type(row.date)==str and row.text and type(row.date)==str):
                if(dateutil.parser.parse(row.date)):
                    tweet_date = dateutil.parser.parse(row.date)
                    tweet_date = tweet_date.strftime("%Y-%m-%d %H:%M:%S")
                    if(valid_date(tweet_date)):
                        tweet_text = re.sub(r'[^A-Za-z0-9ğüşıöçĞÜŞİÖÇ]+', ' ', row.text.strip())
                        turkish_text = translator.translate(tweet_text,dest="tr").text.replace("\r","").replace("\n","")
                        if(turkish_text):
                            data = [row.date,turkish_text]
                            writer.writerow(data)
                    else:
                        continue
                    #tweet_date = dateutil.parser.parse(row.date)
                    #tweet_date = tweet_date.strftime("%Y-%m-%d %H:%M:%S")
        except Exception as e:
            continue
f.close()

