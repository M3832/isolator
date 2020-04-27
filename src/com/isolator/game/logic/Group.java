package com.isolator.game.logic;

import com.isolator.engine.game.GameState;
import com.isolator.engine.core.CollisionBox;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.game.IsolatorGameState;
import com.isolator.game.ai.states.AIDance;
import com.isolator.game.ai.states.AIStand;
import com.isolator.game.ai.states.AIWander;
import com.isolator.game.entity.Visitor;

import java.util.ArrayList;
import java.util.List;

public class Group extends BaseObject {
    private List<Visitor> members;
    private Formation formation;
    private int timeout;

    public Group() {
        members = new ArrayList<>();
        formation = new CircleFormation();
        setTimeout();
    }

    private void setTimeout() {
        timeout = 60 * 30;
    }

    public void addMember(Visitor visitor) {
        members.add(visitor);
    }

    @Override
    public void update(GameState state) {
        timeout--;
        if(members.stream().allMatch(Visitor::isIdle)) {
            decideOnNewAction((IsolatorGameState) state);
        }

        for(Visitor visitor : members) {
            if(visitor.isIdle()) {
                visitor.lookAt(this.position);
            }
        }

        if(timeout <= 0) {
            decideOnNewAction((IsolatorGameState) state);
            setTimeout();
        }
    }

    private void decideOnNewAction(IsolatorGameState state) {
        int randomNumber = state.getRandomGenerator().nextInt(5);
        if(randomNumber == 0) {
            int standTime = state.getRandomGenerator().nextInt(10);
            int standGameTime = state.getGameTimer().getGameTimeFromNow(standTime);

            members.forEach(member -> {
                member.perform(new AIStand(standGameTime));
            });
        } else if(randomNumber == 1) {
            int radius = Math.max(25, 5 * members.size());
            List<Position> walkTargets = formation.calculatePositions(radius, members.size());
            this.position = state.getMap().getRandomAvailableLocation(state, new Size(100, 100));
            for(int i = 0; i < members.size(); i++) {
                Position targetPosition = Position.add(walkTargets.get(i), this.position);
                List<Position> path = state.getPath(targetPosition, members.get(i).getPosition());
                members.get(i).perform(new AIWander(path));
            }
        } else {
            int standTime = state.getRandomGenerator().nextInt(10);
            int standGameTime = state.getGameTimer().getGameTimeFromNow(standTime);

            members.forEach(member -> {
                member.perform(new AIDance(standGameTime));
            });
        }
    }


    @Override
    public CollisionBox getCollisionBox() {
        Size collisionSize = new Size(25 * members.size(), 25 * members.size());
        Position collisionPosition = new Position(
                position.getX() - collisionSize.getWidth() / 2,
                position.getY() - collisionSize.getHeight() / 2
        );

        return CollisionBox.of(collisionPosition, collisionSize);
    }

}
