#Prints summoners from database [primary]
#Created by Ashley Dodson
#April 2017, for UNCA Senior Project
#Uses PyMongo for database controls

import pymongo
from pymongo import MongoClient

def print_server():
    #Create Database Connections
    client = MongoClient("mongodb://doublelift:Graves7!@ds060649.mlab.com:60649/seniorproject")
    db = client.seniorproject

    print("Establishing Connection...")

    if (db.summoners.count() > 0):
        display = db.summoners.find().sort([
            ("summoner", pymongo.ASCENDING)
        ])

        for summoners in display:
            print (summoners)

    else:
        print("Database is empty")


print_server()