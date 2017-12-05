import nltk
import TwitterAPI

# main method
if __name__ == '__main__':
    tweets = TwitterAPI.getTweetIDs("AppleSupport", "", 5)
    for t in tweets:
        print(t)
