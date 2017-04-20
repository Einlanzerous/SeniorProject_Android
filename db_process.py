# Code for pulling data from Riot and inputting it into my database
# Created by Ashley Dodson
# April 2017, for UNCA Senior Project
# Uses PyMongo and Cassiopeia

import os
import time
import math
from cassiopeia import riotapi
from cassiopeia.type.core.common import LoadPolicy, StatSummaryType
from pymongo import MongoClient
import pymongo


def summoner_request(summoner_name):
    # Set API information
    riotapi.set_region("NA")
    riotapi.set_api_key(os.environ["RIOT_DEV"])
    riotapi.set_load_policy(LoadPolicy.lazy)

    # Establish database connection
    client = MongoClient("mongodb://doublelift:Graves7!@ds060649.mlab.com:60649/seniorproject")
    db = client.seniorproject

    # Declare variables
    level = 0
    kills = 0
    deaths = 0
    assists = 0
    csPerGame = 0
    csPerMinute = 0
    csAgainstLaner = 0
    dblKills = 0
    trpKills = 0
    quadKills = 0
    pentaKills = 0
    matchID = []
    avgTime = 0
    score = 0
    totalCS = 0

    try:
        summoner = riotapi.get_summoner_by_name(summoner_name)
        level = summoner.level
    except:
        db.summoners.insert_one(
            {
                "summoner": summoner_name,
                "invalid": True
            }
        )
        return None
    match_list = summoner.match_list()

    # Number of matches being polled from Riot
    num_matches = 8

    # TODO, build a check for current MIDs, and don't poll those
    # Stats collection
    for i, match_reference in enumerate(match_list[0:num_matches]):
        match = match_reference.match()
        for participant in match.participants:
            if participant.summoner_id == summoner.id:
                csPerGame += participant.stats.cs
                kills += participant.stats.kills
                deaths += participant.stats.deaths
                assists += participant.stats.assists
                avgTime += match.duration.total_seconds()
                dblKills += participant.stats.double_kills
                trpKills += participant.stats.triple_kills
                quadKills += participant.stats.quadra_kills
                pentaKills += participant.stats.penta_kills
                matchID.append(match.id)
                if (avgTime > 0):
                    csPerMinute += (float)(participant.stats.cs / (match.duration.total_seconds() / 60))
                    totalCS += participant.stats.cs

    if (num_matches > 0):
        avgTime = (float)((avgTime / 60) / num_matches)
        csPerMinute = (float)(csPerMinute / num_matches)
        csPerGame = (float)(totalCS / num_matches)

    # Calculate Score
    if (level < 30):
        handicap = 1.1
    else:
        handicap = 1
    score = handicap * ((kills + assists - deaths * 2.5) + (csPerGame / 100) + (csPerMinute / 1.5)) + (dblKills * 1.1 + trpKills * 1.5 + quadKills * 3 + pentaKills * 5) - math.sqrt(math.pow(avgTime, 2) - 30)

    # Verify user not already on server, delete if pre-existing
    if (db.summoners.count() > 0):
        if (db.summoners.find({"summoner": summoner.name})):
            try:
                db.summoners.delete_many({"summoner": summoner.name})
                print ("Removed duplicate summoner.")
            except:
                print ("Problem with removing duplicate.")

    # Push to database
    db.summoners.insert_one(
        {
            "summoner": summoner.name,
            "level": level,
            "matches_polled": num_matches,
            "kills": kills,
            "deaths": deaths,
            "assists": assists,
            "cs": {
                "per_game": round(csPerGame, 1),
                "per_minute": round(csPerMinute, 1),
                "against_laner": csAgainstLaner
            },
            "dbl_kills": dblKills,
            "trp_kills": trpKills,
            "quad_kills": quadKills,
            "penta_kills": pentaKills,
            "matches": {
                "mids": matchID,
                "last_update": time.asctime(time.localtime(time.time()))
            },
            "avg_time": round(avgTime, 2),
            "score": round(score, 2)
        }
    )

    # Print data to console if needed
    display = db.summoners.find().sort([
        ("summoner", pymongo.ASCENDING)
    ])

    for summoners in display:
        print (summoners)


#Insert summoner name you want to look up
summoner_request("C9 Ray")

