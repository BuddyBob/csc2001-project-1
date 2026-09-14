public class SessionList {

    public record Node(Session first, Node rest) {

        public static Node insertNode(Node lst, Session sesh) {
            switch (lst) {
                case null:
                    return new Node(sesh, null);

                case Node(Session first, Node rest):
                    if (sesh.date.compareTo(first.date) < 0) {
                        return new Node(sesh, lst);
                    } else {
                        return new Node(first, insertNode(rest, sesh));
                    }
            }
        }
    }
}