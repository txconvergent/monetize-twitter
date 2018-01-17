from django.shortcuts import render
from django.http import HttpResponse

from .forms import QueryForm
from .TwitterAPI import searchTimeLine, usernameExist

# Create your views here.
def index(request):
    print(request.)
    # blank form
    form = QueryForm()

    # if POST request (form submitted)
    if request.method == 'POST':
        # create form instance
        form = QueryForm(request.POST)
        if form.is_valid():
            handle, query, num_tweets = [field.data for field in form]
            if usernameExist(handle):
                tweets = searchTimeLine(handle, query, int(num_tweets))
                handle = "@" + handle
                query = "\"" + query + "\""

                # redirect as successful
                return render(request, "input/search.html", {'tweets': tweets, 'handle': handle, 'query': query})

    # if it's a GET or invalid
    return render(request, "input/index.html", {'form': form})
