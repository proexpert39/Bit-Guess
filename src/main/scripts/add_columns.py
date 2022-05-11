loopCounter = 8

while(loopCounter):
    print(loopCounter)
    with open('tweets/Bitcoin_tweets_'+str(loopCounter)+'.csv', 'r',encoding="utf-8") as original: data = original.read()
    with open('tweets/Bitcoin_tweets_'+str(loopCounter)+'.csv', 'w',encoding="utf-8") as modified: modified.write("user_name,user_location,user_description,user_created,user_followers,user_friends,user_favourites,user_verified,date,text,hashtags,source,is_retweet\n" + data)
    loopCounter+=1
