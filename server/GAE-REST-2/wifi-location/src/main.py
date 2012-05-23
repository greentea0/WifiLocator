import cgi
import os
import datetime
import urllib
import wsgiref.handlers
import csv
import rest

from src.models import Zones, ZoneNames, Friends, Areas, BSSIDZones
from google.appengine.ext.webapp import template
from google.appengine.ext import db
from google.appengine.api import users
from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app


class MainPage(webapp.RequestHandler):
	def get(self):
		'''
		areaReader = csv.reader(open(('surrey_data.csv'),'rU'), delimiter=',')
		
		for row in areaReader:
			Areas(zone_id=int(row[0]),zone_name=row[1]).put()
	
		
		macReader = csv.reader(open(('surrey_res.csv'),'rU'), delimiter=',')
		

    		for row in macReader:
			curr_area = Areas.all()
			temp = curr_area.filter("zone_id =", (int(row[1])+20))
			#print row[1]
			#print "\n"
			for area in temp:
				#print "[" + str(area.zone_id) + "]"
				BSSIDZones(zones = area, mac_address = row[0]).put()
		'''	
		bssid_query = db.GqlQuery("SELECT * "
				"FROM BSSIDZones ")
		area_query = db.GqlQuery("SELECT * "
						"FROM Areas "
						"ORDER BY zone_id")
		friend_query = db.GqlQuery("SELECT * "
					"FROM Friends ")
		template_values = {
    		'bssid': bssid_query,
		'areas': area_query,
		'friends' : friend_query,
		}
		path = os.path.join(os.path.dirname(__file__), 'index.html')
		self.response.out.write(template.render(path,template_values))



application = webapp.WSGIApplication([('/', MainPage), ('/rest/.*', rest.Dispatcher)], debug=True)

# configure the rest dispatcher to know what prefix to expect on request urls
rest.Dispatcher.base_url = "/rest"

# add all models from the current module, and/or...
rest.Dispatcher.add_models_from_module(__name__)

# add all models from some other module, and/or...
#rest.Dispatcher.add_models_from_module(my_model_module)
# add specific models
#rest.Dispatcher.add_models({"zones": Zones})
  
# add specific models (with given names) and restrict the supported methods
#rest.Dispatcher.add_models({'zones' : (Zones, rest.READ_ONLY_MODEL_METHODS)})

# use custom authentication/authorization
#rest.Dispatcher.authenticator = MyAuthenticator()
#rest.Dispatcher.authorizer = MyAuthorizer()
                                     
def main():
    run_wsgi_app(application)

if __name__ == "__main__":
    main()