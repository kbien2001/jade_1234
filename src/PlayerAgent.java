package jadelab2;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.Arrays;
import java.util.Random;

public class PlayerAgent extends Agent {
    private static final String[] ACTIONS = {"paper", "rock", "scissors"};
    private double[] strategy = {33, 33, 34};
    private Random rand = new Random();

    @Override
    protected void setup() {
        addBehaviour(new TickerBehaviour(this, 5000) {
            @Override
            protected void onTick() {
                // Choosing an action based on the current strategy
                String action = chooseAction();
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.setSender(getAID());
                msg.addReceiver(new AID("catalogueManager", AID.ISLOCALNAME));
                msg.setContent(action);
                send(msg);

                // Print the name of the current agent and its chosen action
                System.out.println(this.getAgent().getLocalName() + ": " + action);

                // Wait for outcome and update strategy

                ACLMessage reply = receive();
                if (reply != null) {
                    String outcome = reply.getContent();
                    updateStrategy(outcome);
                } else {
                    block();
                }
            }

            private String chooseAction() {

                int index = rand.nextInt(100);
                int pom;

                if (index > 0 && index <= strategy[0])
                {
                    pom = 0;
                }
                else if (index > strategy[0] && index <= (strategy[0] + strategy[1]))
                {
                    pom = 1;
                }
                else
                {
                    pom=2;
                }

                return ACTIONS[pom];

            }
            private void updateStrategy(String outcome) {
                boolean stra=false;
                int count = 0;
                for (int i = 0; i < ACTIONS.length; i++) {
                    if (ACTIONS[i].equals(outcome)) {
                        stra = true;
                      count=i;


                    }

                } if(stra==true) {
                    for (int z = 0; z < strategy.length; z++) {
                        if (z == count) {
                            strategy[z] = strategy[z] + 2;
                        } else {
                            strategy[z] = strategy[z] - 1;
                        }
                    }
                    System.out.println(getAID().getLocalName() + " updated strategy: " + Arrays.toString(strategy));
                }

            }
        });
    }
}
