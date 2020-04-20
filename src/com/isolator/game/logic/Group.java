package com.isolator.game.logic;

import com.isolator.engine.GameState;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.game.IsolatorGameState;
import com.isolator.game.ai.states.AIStand;
import com.isolator.game.ai.states.AIWander;
import com.isolator.game.entity.Visitor;

import java.util.ArrayList;
import java.util.List;

public class Group extends BaseObject {
    private List<Visitor> members;
    private Formation formation;

    public Group() {
        members = new ArrayList<>();
        formation = new CircleFormation();
    }

    public void addMember(Visitor visitor) {
        members.add(visitor);
    }

    @Override
    public void update(GameState state) {
        if(members.stream().allMatch(Visitor::isIdle)) {
            decideOnNewAction((IsolatorGameState) state);
        }

        for(Visitor visitor : members) {
            if(visitor.isIdle()) {
                visitor.lookAt(this.position);
            }
        }
    }

    private void decideOnNewAction(IsolatorGameState state) {
        int randomNumber = state.getRandomGenerator().nextInt(2);
        if(randomNumber == 0) {
            int standTime = state.getRandomGenerator().nextInt(10);
            int standGameTime = state.getGameTimer().getGameTimeFromNow(standTime);

            members.forEach(member -> {
                member.perform(new AIStand(standGameTime));
            });
        } else {
            int radius = Math.max(25, 5 * members.size());
            List<Position> walkTargets = formation.calculatePositions(radius, members.size());
            this.position = state.getMap().getRandomAvailableLocation(state, new Size(100, 100));

            for(int i = 0; i < members.size(); i++) {
                Position targetPosition = Position.add(walkTargets.get(i), this.position);
                members.get(i).perform(new AIWander(targetPosition));
            }
        }
    }


}