import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void insertNodeTest() {

        Session s1 = new Session(1, "Title", "Mentor", "2026-09-14", "Location", 10);

        SessionList.Node result = SessionList.Node.insertNode(null, s1);

        assertEquals(1, result.first().id);
        assertNull(result.rest());
        Session s2 = new Session(1, "Title", "Mentor", "2026-09-10", "Location", 10);
        Session s3 = new Session(2, "Title", "Mentor", "2026-09-05", "Location", 10);

        SessionList.Node list = new SessionList.Node(s2, null);
        SessionList.Node result2 = SessionList.Node.insertNode(list, s3);

        // s2 has the earlier date, so it should be first now
        assertEquals(2, result2.first().id);
        assertEquals(1, result2.rest().first().id);
    }

}
