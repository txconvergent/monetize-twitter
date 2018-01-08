from django import forms

class QueryForm(forms.Form):
    handle = forms.CharField(label="handle", max_length=50,
                widget=forms.TextInput(attrs={'placeholder': "Enter @name...",
                                              'class': 'form-control'}))
    query = forms.CharField(label="query",max_length=200,
                widget=forms.TextInput(attrs={'placeholder': "What's your question?",
                                            'class': 'form-control'}))
    num_tweets = forms.IntegerField(label="num_tweets", min_value=1, max_value=20,
                widget=forms.TextInput(attrs={'placeholder': "How many replies?",
                                            'class': 'form-control'}))
