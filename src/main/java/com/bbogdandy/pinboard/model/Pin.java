package com.bbogdandy.pinboard.model;

import com.bbogdandy.pinboard.types.PinType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Data
@Entity
@Table(name = "pins")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Pin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo owner;
    private int x;
    private int y;
    private String content;
    private String Color;
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
    private LocalDate appearAt;
    private LocalDate disappearAt;

    @OneToMany(mappedBy = "from", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    private List<Connection> connectionsFrom = new ArrayList<>();

    @OneToMany(mappedBy = "to", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Connection> connectionsTo = new ArrayList<>();

    public static Set<Pin> getConnectedPinsByColor(Pin start, String color) {
        Set<Pin> visited = new HashSet<>();
        Deque<Pin> stack = new ArrayDeque<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            Pin current = stack.pop();
            if (!visited.add(current)) continue;

            // check outgoing connections
            for (Connection conn : current.getConnectionsFrom()) {
                if (color.equals(conn.getColor())) {
                    stack.push(conn.getTo());
                }
            }
            for (Connection conn : current.getConnectionsTo()) {
                if (color.equals(conn.getColor())) {
                    stack.push(conn.getFrom());
                }
            }
        }
        return visited;
    }
}
