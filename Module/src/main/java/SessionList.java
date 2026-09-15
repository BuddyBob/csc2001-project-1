public class SessionList {

    // Purpose:
    // return true if a Session with the given id already exists in the list
    public static boolean containsId(Node lst, int id) {  // adjust type of id to match Session
        return switch (lst) {
            case null -> false;
            case Node(Session first, Node rest) ->
                    first.id == id || containsId(rest, id);   // adjust field/method name
        };
    }

    public record Node(Session first, Node rest) {

        public static Node insertNode(Node lst, Session sesh) {

            if (containsId(lst, sesh.id)) {
                throw new IllegalArgumentException("Session with this ID already exists");
            }
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