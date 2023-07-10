# wot-stat-tracker
Spring Web App that persists, updates and shows WoT players stat

Via application_id, every 10 min parse data from Wargaming API about requested users and show stat's history on frontend.
App should have ability to:
- ask for wanted username to start track stat for
- persist usernames and ids in database
- request WG API data about saved users every 10 mins
- display charts, main stat - WN8, WGR, WR, money balance...
  
EU server only
