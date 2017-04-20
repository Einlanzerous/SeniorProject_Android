#Code for requesting summoner names from main database collection
#Created by Ashley Dodson
#April 2017, for UNCA Senior Project
#Uses PyMongo and Cassiopeia

from pymongo import MongoClient
import pymongo
import db_process

def database_check():
    #Create database connections
    requests = MongoClient("mongodb://doublelift:Graves7!@ds060649.mlab.com:60649/seniorproject")
    db = requests.seniorproject

    print("Establishing Connection...")


    while (1):
        if(db.requests.count() > 0):
            print("Database not empty, checking for new requests")

            evaluate_list = db.requests.find()

            current_name = evaluate_list[0]["name"]

            print("Requesting ", current_name, " be added to main collection with datapull.")

            try:
                db_process.summoner_request(current_name)
            except:
                print("Name Invalid")

            if (db.requests.find({"name": current_name})):
                db.requests.delete_many({"name": current_name})
                print("Removed request")

            print("Awaiting new requests...")

#Set script to run- will await new requests
database_check()