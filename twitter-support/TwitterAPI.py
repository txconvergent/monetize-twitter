'''
I made a twitter account: @ConvergentSupp
                password: convergent

We need this account to be able to generate the keys shown below,
which in turn allows us to retrieve use the API interface.
'''

# API Keys generated from apps.twitter.com


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

Other (potentially) useful methods:
    GetUserTimeline: directly access a specific user's timeline
        api.GetUserTimeline(screen_name="Username")
    GetFfriends: not as useful, but access a user's friends list
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


#simple function tests getting search input and separates words
#company name is first element of list returned
def getInput():
    search = input("Please enter a search in the format @<username> <string>\n").split(' ')
    search[0] = search[0].replace('@', '')
    return search




#function to parse tweets and assigns a quantified 'relevance' to each tweet
#unfinished
def searchTimeLine(search):

    statuses = api.GetUserTimeline(screen_name=search[0])

    '''Perhaps formulize relevance based on keywords matched, retweets, and replies
            perhaps: a*keyword_count + b*(retweets/10) + c*(other_factors)
       Then return list of tweets in relevant order
       next function can determine threads from those tweets
       finally print out
    '''

    print([s.text for s in statuses])


'''Test for grabbing the original tweet from a reply'''
def original_tweet(t):
    for reply in t:
        if reply.in_reply_to_status_id is not None:
            tweet = api.GetStatus(status_id=reply.in_reply_to_status_id)
            print tweet.text


# main method in python, temp for testing
if __name__ == '__main__':
    smallTest()
    search = getInput()
    searchTimeLine(search)
    original_tweet(api.GetSearch(term="applesupport", count=5))
