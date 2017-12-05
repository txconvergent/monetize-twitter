import nltk
import TwitterAPI

# main method
if __name__ == '__main__':
    tweets = TwitterAPI.getTweetIDs("@AppleSupport", "cracked screen", 5)
    for t in tweets:
        print(t)
