# casino-jack

## Jenkins CI
Clone repository into /home/Documents/

Steps to set up CI configuration:
1. Change .env.example to .env and change HOME to your own home variable
2. Run docker network create jenkins
3. Run docker-compose up -d (detached: run in the background)
4. Open your browser at localhost:8081

Go to https://www.jenkins.io/doc/tutorials/build-a-java-app-with-maven/#setup-wizard for the next steps.

## Distribution of work

Creating tests and implementation for the behaviour of specific classes at least needs to be distributed according to the next rules:

* Qi-Mo: cashier, bettinground, abstractgame, BetID

* Mikel: defaultgame, gamblercard, BettingRoundID, GamingMachineID

* Stoyan: gamingmachine, gamerule, idfactory, CardID 
