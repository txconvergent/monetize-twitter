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
from django.conf import settings
from nltk.corpus import stopwords

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
api = initializeAPIfromfile(settings.BASE_DIR + "/input/static/keys.txt")
debug("API initialized!")

# setup stopwords
stopWords = set(stopwords.words('english'))

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

# given a String, filters out the stopwords
def filterStopWords(text):
    filtered = []
    for word in text.strip().split(" "):
        if word not in stopWords:
            filtered.append(word)
    return " ".join(filtered)

# determines relevance based on the amount of times keywords appear in text
def relevance(query, text):
    text = filterStopWords(text).lower()
    score = 0
    for word in query.strip().split(" "):
        score += text.count(word)
    return score

# Front End Method: Determines whether username exists
def usernameExist(profile):
    try:
        api.GetUserTimeline(screen_name=profile)
        return True
    except twitter.error.TwitterError:
        return False

# Front End Method: Takes profile, query, and num tweets and returns tweets to display
def searchTimeLine(profile, query, numTweets):
    # the amount of tweets to parse
    parsing = 100

    # filter stopwords from query
    query = filterStopWords(query.lower())
    
    # get a list of recent tweets from support handle
    statuses = api.GetUserTimeline(screen_name=profile, count=parsing) # seems to max out at 200
    debug("Loaded %d tweets!" % len(statuses))

    # filter out tweets that aren't replies
    debug("Filtering out non-responses...")
    replies = []
    for s in statuses:
        try:
            if (s.in_reply_to_status_id):
                replies.append([s, api.GetStatus(s.in_reply_to_status_id)])
        except twitter.error.TwitterError:
            pass
    debug("%d tweets remaining after filtering out non-responses" % len(replies))

    # assign relevance score
    debug("Assigning relevance...")
    for r in replies:
        response, question = r[0], r[1]
        score = 0
        score += (20 * (relevance(query, question.text) + relevance(query, response.text)))
        score += (question.favorite_count + response.favorite_count)
        score += (5 * (question.retweet_count + response.favorite_count))
        r.append(score)

    # sort by relevance
    replies.sort(key=lambda x: x[2], reverse=True) 
    numTweets = min(numTweets, len(replies))
    return [r[0].id for r in replies[:numTweets]]


# main method in python, temp for testing
if __name__ == '__main__':
    print(searchTimeLine("AppleSupport", " broken iphone", 10))
