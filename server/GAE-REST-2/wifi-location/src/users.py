import cgi
import os
import datetime
import urllib
import wsgiref.handlers
import csv
import rest
from django.utils import simplejson as json

from src.models import *

from google.appengine.ext import db
from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app

class SetUser(webapp.RequestHandler):
	def get(self):
		self.response.out.write("requires post or delete request")
	def post(self):
		json_obj = json.loads(self.request.body)
		if json_obj["command"] == "adduser":
			Users(first_name = json_obj["first_name"], short_name = json_obj["short_name"], last_name = json_obj["last_name"]).put()
			self.response.out.write("user_added")
		elif json_obj["command"] == "deleteuser":
			userobj = db.GqlQuery(("SELECT * FROM Users " + "WHERE short_name = :1"), urllib.unquote_plus(json_obj["short_name"]))
			if userobj.count() <= 0:
				self.response.out.write("user_not_found")
			else:
				userobj[0].delete()
				self.response.out.write("user_deleted")
		else:
			self.response.out.write("invalid_command")
