package jadelab2;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class CatalogueManagerAgent extends Agent {
    private String lastActionPlayer1;
    private String lastActionPlayer2;

    @Override
    protected void setup() {
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
                ACLMessage msg = myAgent.receive(mt);
                if (msg != null) {
                    String content = msg.getContent();

                        processAction(msg);
                    }

            }

            private void processAction(ACLMessage msg) {
                String action = msg.getContent();
                if (msg.getSender().getLocalName().equals("player1")) {
                    lastActionPlayer1 = action;
                } else if (msg.getSender().getLocalName().equals("player2")) {
                    lastActionPlayer2 = action;
                }
                if (lastActionPlayer1 != null && lastActionPlayer2 != null) {
                    determineOutcome();
                }
            }

            private void determineOutcome() {
                String outcome;
                if (lastActionPlayer1.equals(lastActionPlayer2)) {
                    outcome = "tie";
                    System.out.println("It's a tie!");
                }
                else if ((lastActionPlayer1.equals("rock") && lastActionPlayer2.equals("scissors")) ||
                        (lastActionPlayer1.equals("scissors") && lastActionPlayer2.equals("paper")) ||
                        (lastActionPlayer1.equals("paper") && lastActionPlayer2.equals("rock"))) {
                    outcome = lastActionPlayer1;
                    System.out.println("Player 1 wins!");
                } else {
                    outcome = lastActionPlayer2;
                    System.out.println("Player 2 wins!");
                }
                informPlayers(outcome);
                lastActionPlayer1 = null;
                lastActionPlayer2 = null;
            }

            private void informPlayers(String outcome) {
                ACLMessage outcomeMsg = new ACLMessage(ACLMessage.INFORM);
                outcomeMsg.setContent(outcome);
                outcomeMsg.addReceiver(new AID("player1", AID.ISLOCALNAME));
                outcomeMsg.addReceiver(new AID("player2", AID.ISLOCALNAME));
                send(outcomeMsg);
                System.out.println("Outcome: " + outcome);
            }
        });
    }
}