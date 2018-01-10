(function() {
  // quota is the maximum number of events we'll send tosentry per client instance
  var quota = 2;

  window.Raven.config('https://' + window.sentry_api_key + '@sentry.io/77589', {
    release: window.sentry_version_id,
    ignoreErrors: [
      // Random plugins/extensions
      'top.GLOBALS',
      // See: http://blog.errorception.com/2012/03/tale-of-unfindable-js-error.html
      'originalCreateNotification',
      'canvas.contentDocument',
      /Refused to connect to '(?!https:\/\/[^.*]\.?(twimg|twitter)\.com)/
    ], shouldSendCallback: function(data) {
      if (!data) { data = {}; }
      var willSend = !!(quota && quota--) && !/^(.*Chrome\/20\.0.*)$/.test(window.navigator.userAgent);

      return willSend;
    }
  }).install();

  if (typeof location.search === 'string' && location.search.indexOf('trigger-test-error-for-raven') >= 0) {
    throw new Error('I AM ERROR. (testing raven.)');
  }

  var i, err;

  var extra = {
    extra: {error_on_init: true}
  };

  if (window.initErrorstack) {
    for (i = 0; i < window.initErrorstack.length; i++) {
      err = window.initErrorstack[i];

      if (!err.errorObj) {
        window.Raven.captureException(err.errorObj, extra);
      } else {
        window.Raven.captureMessage(err.errorObj + '(' + err.url + ':' + err.lineNumber + ')', extra);
      }
    }
  }
}());
