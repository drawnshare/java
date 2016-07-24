package com.esgi.vMail.view;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import org.jivesoftware.smack.packet.Presence;

import java.util.ArrayList;

public class StatusRound extends Circle {
	private Status status;

	public Status getStatus() {
		return status;
	}

	public enum Status {
		ONLINE,
		AWAY,
		BUSY,
		HIDDEN,
		OFFLINE
	}
	private StatusRound(Status status) {
		this.status = status;
		this.setRadius(9);
		this.setStroke(Color.TRANSPARENT);
		this.setStrokeType(StrokeType.INSIDE);
		this.setFill(StatusRound.determineRadial(status));
	}

	private static Status determineStatusByPresence(Presence presence) {
		Status status;
		switch (presence.getType()) {
		case available:
			System.out.print("=> dispo");

			switch (presence.getMode()) {
			case available:
				System.out.println(" = tres dispo");
				status = Status.ONLINE;
				break;
			case chat:
				System.out.println(" = tres tres dispo");
				status = Status.ONLINE;
				break;
			case away:
				System.out.println(" = AFK");
				status = Status.AWAY;
				break;
			case xa:
				System.out.println(" = long AFK");
				status = Status.AWAY;
				break;
			case dnd:
				System.out.println(" = DND");
				status = Status.BUSY;
				break;
			default:
				System.out.println(" = WTF Oo");
				status = Status.ONLINE;
				break;
			}
			break;
		default:
			System.out.println("=> pas dispo");
			status = Status.OFFLINE;
			break;
		}
		return status;
	}

	private static RadialGradient determineRadial(Status status) {
		ArrayList<Stop> stops = new ArrayList<>();
		switch (status) {
		case ONLINE:
			stops.add(new Stop(0, Color.web("#008d02")));
			stops.add(new Stop(0.5, Color.web("#00fc00")));
			stops.add(new Stop(1, Color.web("#05fa0580")));
			break;
		case AWAY:
			stops.add(new Stop(0, Color.web("#8c6200")));
			stops.add(new Stop(0.5, Color.web("#fab900")));
			stops.add(new Stop(1, Color.web("#ffaa0182")));
			break;
		case BUSY:
			stops.add(new Stop(0, Color.web("#940000")));
			stops.add(new Stop(0.5, Color.web("#fa0000")));
			stops.add(new Stop(1, Color.web("#ff03037f")));
			break;
		case HIDDEN:
			stops.add(new Stop(0, Color.web("#363535")));
			stops.add(new Stop(0.5, Color.web("#616161")));
			stops.add(new Stop(1, Color.web("#fffefe80")));
			break;
		case OFFLINE:
			stops.add(new Stop(0, Color.web("#0a0a0a")));
			stops.add(new Stop(0.5, Color.web("#4d4d4d8d")));
			stops.add(new Stop(1, Color.web("#82828282")));
			break;
		default:
			break;
		}
		return new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, stops);
	}

	public static StatusRound set(Status status) {
		return new StatusRound(status);
	}

	public static StatusRound set(Presence presence) {
		return StatusRound.set(StatusRound.determineStatusByPresence(presence));
	}

	public static void update(Circle instance, Status status) {
		instance.setFill(StatusRound.determineRadial(status));
	}

	public static void update(Circle instance, Presence presence) {
		StatusRound.update(instance, StatusRound.determineStatusByPresence(presence));
	}
}
