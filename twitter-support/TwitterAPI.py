'''
I made a twitter account: @ConvergentSupp
                password: convergent

We need this account to be able to generate the keys shown below,
which in turn allows us to retrieve use the API interface.
'''

# API Keys generated from apps.twitter.com
consumer_key = 'wKtkBMoS9zCczcXNSpmBQmANv'
consumer_secret = '3UtsuLRU0pLGQME4PI4BDAILHQ2qKLv9RL4eChOSvVOSfJ79Jr'
access_token_key = '928484335031914496-dpXe6Lk6iRShm80XzR3GMnl1hOorhk7'
access_token_secret = 'ji94aVMvSCKBlEneis3xIMInmQkrjaCfgWT1OWMbBbRc6'

'''
Twitter maintains a Python repository that with full API 
functionality; it's probably our best option.
You download it from command line with:
    pip install python-twitter
(should work w/ Windows, MacOS, and Linux)
Otherwise, follow the instructions from the Github ReadMe (scroll down):
https://github.com/bear/python-twitter
'''
import twitter

# Initiallizing the API w/ @ConvergentSupp keys
api = twitter.Api(consumer_key=consumer_key,
                      consumer_secret=consumer_secret,
                      access_token_key=access_token_key,
                      access_token_secret=access_token_secret)


'''
The API is pretty dense for documentation, so here's
a brief overview of the GetSearch method's parameters.
They are all optional parameters, so they must be 
entered in the form "term='my iphone sucks wtf'"
    term - the search term
    geolocation - lat,long,radius i.e. geocode="37.781157,-122.398720,1mi"
    count - number of tweets to return, default 15 if unspecified
    include_entities - includes metadata including hashtags and user_mentions (LOOK AT LATER)

    Returns a list of twitter.Status instances, which are accessed as described below.

GetSearch returns a list of twitter.Status instances, which are even
harder to find API for. For a twitter.Status object called tweet, we have:
    tweet.user.screen_name - username of tweeter
    tweet.user.default_profile_image - profile image of tweeter
    tweet.text - text of tweet
    tweet.fulltext - full text if tweet.tweet_mode == 'extended'
    tweet.favorite_count - num of favorites
    tweet.retweet_count - num of retweets
    tweet.hashtags - List of HashTag objects, who's text is in hashtag.text
    tweet.id - used for identifying tweets
    tweet.in_reply_to_screen_name - profile this tweet is replying to
    tweet.in_reply_to_status_id - id of tweet replying to (YESSS THIS WILL BE USEFUL)
'''
def smallTest():
    print("Small Scale Test for AppleSupport")
    for tweet in api.GetSearch(term="applesupport", count=5):
        print("-- Tweet -- ")
        print("Username: @" + tweet.user.screen_name) 
        print(tweet.fulltext if hasattr(tweet, 'fulltext') else tweet.text)
        print("Retweets: " + str(tweet.retweet_count))
        print("Favorties: " + str(tweet.favorite_count))
        print("Hashtags: " + ", ".join([ht.text for ht in tweet.hashtags]))
        print("")

# main method in python, temp for testing 
if __name__ == '__main__':
    smallTest()
