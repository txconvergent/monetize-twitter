'''
I made a twitter account: @ConvergentSupp
                password: **********

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
import string
import sys, os
import io
import nltk

# if True print outs debug statements
DEBUG_ON = True

# print method for debugging
def debug(x):
    if DEBUG_ON:
        print(x)

# Initializes Twitter API from input file
def initializeAPIfromfile(filename):
    api = None
    keys = []
    with open(filename) as f:
        keys = [line.strip() for line in f]
        api = twitter.Api(consumer_key=keys[0],
                          consumer_secret=keys[1],
                          access_token_key=keys[2],
                          access_token_secret=keys[3])
    return api

debug("Initializing API...")
api = initializeAPIfromfile("keys.txt")
debug("API initialized!")

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
    search = input("Please enter a search in the format @<username> <string>\n").strip().split(' ')
    search[0] = search[0].replace('@', '')
    return search

# determines relevance based on the amount of times keywords appear in text
def relevance(query, text):
    score = 0
    for word in query:
        score += query.count(word)
    return score

#function to parse tweets and assigns a quantified 'relevance' to each tweet
#unfinished
def searchTimeLine(profile, text, numTweets):
    # get a list of recent tweets from support handle
    statuses = api.GetUserTimeline(screen_name=profile, count=100) # seems to max out at 200
    debug("Loaded %d tweets!" % len(statuses))

    # filter out tweets that aren't replies
    replies = []
    for s in statuses:
        try:
            replies.append((s, api.GetStatus(s.in_reply_to_status_id))
        except twitter.error.TwitterError:
            pass
    debug("%d tweets remaining after filtering out non-responses" % len(replies))

    # assign relevance score


# front-end method #2, takes in profile, search, number_of_tweets, returns tweet ID's of AppleSupport responses
def getTweetIDs(profile, search, numTweets):
    tweets = get_tweets_by_search(profile, search, numTweets)
    return [t[1].id for t in tweets]

# main method in python, temp for testing
if __name__ == '__main__':
    searchTimeLine("AppleSupport", "broken screen")
