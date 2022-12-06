package aoc2019;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Aoc1906 {
    public static void main(String[] args) {
        var raw = (
            "COM)B\n" +
            "B)C\n" +
            "C)D\n" +
            "D)E\n" +
            "E)F\n" +
            "B)G\n" +
            "G)H\n" +
            "D)I\n" +
            "E)J\n" +
            "J)K\n" +
            "K)L\n" +
            "K)YOU\n" +
            "I)SAN"
        ).split("\\n");

        var root = new Body("COM");

        Map<String, Body> map = new HashMap<>();
        map.put("COM", root);

        for (var rel : raw) {
            var parentName = rel.split("\\)")[0];
            var childName = rel.split("\\)")[1];

            var parent = map.get(parentName);
            if (parent == null) {
                parent = new Body(parentName);
                map.put(parentName, parent);
            }

            var child = map.get(childName);
            if (child == null) {
                child = new Body(parent, childName);
                map.put(childName, child);
            } else {
                child.setParent(parent);
            }
        }

        Set<String> santasPathToCom = map.get("SAN").getPathTo("COM");
        var intersection = map.get("YOU").findOneOf(santasPathToCom);
        var yoursPath = map.get("YOU").getPathTo(intersection);
        var santas = map.get("SAN").getPathTo(intersection);

        System.out.println(yoursPath.size() + santas.size() - 4);
    }

    static class Body {
        private Body parent = null;
        private String name;

        public Body(String name) {
            this.name = name;
        }

        public Body(Body parent, String name) {
            this.parent = parent;
            this.name = name;
        }

        public void setParent(Body parent) {
            this.parent = parent;
        }

        public Set<String> getPathTo(String to) {
            Set<String> set = new HashSet<>();
            set.add(name);
            if (parent != null && !name.equals(to)) {
                set.addAll(parent.getPathTo(to));
            }
            return set;
        }

        public String findOneOf(Set<String> set) {
            if (set.contains(name) || parent == null) {
                return name;
            }
            return parent.findOneOf(set);
        }
    }
}
