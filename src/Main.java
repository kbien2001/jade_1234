package jadelab2;

import jade.core.AID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import jade.core.Runtime;

public class Main {
    public static void main(String[] args) {
        Profile profile = new ProfileImpl();
        ContainerController cc = Runtime.instance().createMainContainer(profile);
        try {
            cc.acceptNewAgent("catalogueManager", new CatalogueManagerAgent()).start();
            cc.acceptNewAgent("player1", new PlayerAgent()).start();
            cc.acceptNewAgent("player2", new PlayerAgent()).start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }

    }
}