package com.isolator.game.map;

import com.isolator.engine.core.CollisionBox;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.game.entity.BaseEntity;

import java.util.*;
import java.util.stream.Collectors;

public class Pathfinder {
    private boolean[][] collisionMap;
    private Size gridSize;

    public Pathfinder(List<BaseObject> objects, int xTiles, int yTiles) {
        collisionMap = new boolean[xTiles][yTiles];
        gridSize = new Size(64, 64);
        initCollisionMap(objects);
    }

    private void initCollisionMap(List<BaseObject> objects) {
        List<BaseObject> collect = objects.stream()
                .filter(o -> !(o instanceof BaseEntity))
                .collect(Collectors.toList());

        for(int x = 0; x < collisionMap.length; x++) {
            for(int y = 0; y < collisionMap[0].length; y++) {
                final CollisionBox currentTile = CollisionBox.of(
                        new Position(x * gridSize.getWidth(), y * gridSize.getHeight()),
                        gridSize);

                collisionMap[x][y] = collect.stream().anyMatch(o -> o.getCollisionBox().checkCollision(currentTile));
            }
        }
    }

    public List<Position> findPathTo(Position targetPath, Position startPath) {
        Position startPosition = new Position(startPath.getX() / 64, startPath.getY()/64);
        Position targetPosition = new Position(targetPath.getX() / 64, targetPath.getY()/64);

        Queue<Node> openSet = new PriorityQueue<>();
        Map<Position, Node> allNodes = new HashMap<>();

        Node startNode = new Node(startPosition, null, 0, startPosition.distanceTo(targetPosition));
        openSet.add(startNode);
        allNodes.put(startPosition, startNode);

        while(!openSet.isEmpty()) {
            Node next = openSet.poll();
            if(next.position.equals(targetPosition)) {
                return getFinalRoute(next);
            }

            for(int x = next.position.getX() - 1; x <= next.position.getX() + 1; x++) {
                for(int y = next.position.getY() - 1; y <= next.position.getY() + 1; y++) {
                    Position nextPosition = new Position(x, y);
                    if(nextPosition.equals(next.position) || isOutOfBounds(nextPosition) || collisionMap[x][y])
                        continue;

                    Node nextNode = allNodes.getOrDefault(nextPosition, new Node(nextPosition));
                    allNodes.put(nextPosition, nextNode);

                    double newScore = next.routeScore + nextPosition.distanceTo(next.position);
                    if(newScore < nextNode.routeScore) {
                        nextNode.previous = next;
                        nextNode.routeScore = newScore;
                        nextNode.estimatedScore = newScore + nextPosition.distanceTo(targetPosition);
                        openSet.add(nextNode);
                    }
                }
            }
        }
        System.out.println("Found no route");
        return Collections.emptyList();
    }

    private boolean isOutOfBounds(Position exploredPosition) {
        return exploredPosition.getX() < 0 || exploredPosition.getX() >= collisionMap.length
                || exploredPosition.getY() < 0 || exploredPosition.getY() >= collisionMap[0].length;
    }

    private List<Position> getFinalRoute(Node next) {
        List<Position> route = new ArrayList<>();
        Node current = next;
        do {
            route.add(0, new Position(current.position.getX() * 64 + 32, current.position.getY() * 64 + 32));
            current = current.previous;
        } while(current != null);

        return route;
    }

    private static class Node implements Comparable<Node> {
        Position position;
        Node previous;
        double routeScore;
        double estimatedScore;

        public Node(Position position) {
            this(position, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        }

        public Node(Position position, Node previous, double routeScore, double estimatedScore) {
            this.position = position;
            this.previous = previous;
            this.routeScore = routeScore;
            this.estimatedScore = estimatedScore;
        }

        @Override
        public int compareTo(Node other) {
            if (this.estimatedScore > other.estimatedScore) {
                return 1;
            } else if (this.estimatedScore < other.estimatedScore) {
                return -1;
            } else {
                return 0;
            }
        }
    }


}
