package com.esgi.vMail.view;

import java.util.ArrayList;

import com.sun.glass.ui.TouchInputSupport;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class StatusRound extends Circle {
	public enum Status {
		ONLINE,
		AWAY,
		BUSY,
		HIDDEN,
		OFFLINE
	}
	private StatusRound(Status status) {
		this.setRadius(9);
		this.setStroke(Color.TRANSPARENT);
		this.setStrokeType(StrokeType.INSIDE);
		this.setFill(StatusRound.determineRadial(status));
	}

	private static RadialGradient determineRadial(Status status) {
		ArrayList<Stop> stops = new ArrayList<>();
		switch (status) {
		case ONLINE:
//			<Circle radius="9.0" stroke="TRANSPARENT" strokeType="INSIDE">
//            <fill>
//               <RadialGradient centerX="0.5" centerY="0.5" radius="0.5">
//                  <stops>
//                     <Stop color="#008d02" />
//                     <Stop color="#00fc00" offset="0.5092592592592593" />
//                     <Stop color="#05fa0580" offset="1.0" />
//                  </stops>
//               </RadialGradient>
//            </fill></Circle>
			stops.add(new Stop(0, Color.web("#008d02")));
			stops.add(new Stop(0.5, Color.web("#00fc00")));
			stops.add(new Stop(1, Color.web("#05fa0580")));
			break;
		case AWAY:
//			<Circle radius="9.0" stroke="TRANSPARENT" strokeType="INSIDE">
//            <fill>
//               <RadialGradient centerX="0.5" centerY="0.5" radius="0.5">
//                  <stops>
//                     <Stop color="#8c6200" />
//                     <Stop color="#fab900" offset="0.5092592592592593" />
//                     <Stop color="#ffaa0182" offset="1.0" />
//                  </stops>
//               </RadialGradient>
//            </fill></Circle>
			stops.add(new Stop(0, Color.web("#8c6200")));
			stops.add(new Stop(0.5, Color.web("#fab900")));
			stops.add(new Stop(1, Color.web("#ffaa0182")));
			break;
		case BUSY:
//			<Circle radius="9.0" stroke="TRANSPARENT" strokeType="INSIDE">
//            <fill>
//               <RadialGradient centerX="0.5" centerY="0.5" radius="0.5">
//                  <stops>
//                     <Stop color="#940000" />
//                     <Stop color="#fa0000" offset="0.5092592592592593" />
//                     <Stop color="#ff03037f" offset="1.0" />
//                  </stops>
//               </RadialGradient>
//            </fill></Circle>
			stops.add(new Stop(0, Color.web("#940000")));
			stops.add(new Stop(0.5, Color.web("#fa0000")));
			stops.add(new Stop(1, Color.web("#ff03037f")));
			break;
		case HIDDEN:
//			<Circle radius="9.0" stroke="TRANSPARENT" strokeType="INSIDE">
//            <fill>
//               <RadialGradient centerX="0.5" centerY="0.5" radius="0.5">
//                  <stops>
//                     <Stop color="#363535" />
//                     <Stop color="#616161" offset="0.5092592592592593" />
//                     <Stop color="#fffefe80" offset="1.0" />
//                  </stops>
//               </RadialGradient>
//            </fill></Circle>
			stops.add(new Stop(0, Color.web("#363535")));
			stops.add(new Stop(0.5, Color.web("#616161")));
			stops.add(new Stop(1, Color.web("#fffefe80")));
			break;
		case OFFLINE:
//			 <Circle radius="9.0" stroke="TRANSPARENT" strokeType="INSIDE">
//             <fill>
//                <RadialGradient centerX="0.5" centerY="0.5" radius="0.5">
//                   <stops>
//                      <Stop color="#0a0a0a" />
//                      <Stop color="#4d4d4d8d" offset="0.5055555555555555" />
//                      <Stop color="#82828282" offset="1.0" />
//                   </stops>
//                </RadialGradient>
//             </fill></Circle>
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

	public static void update(StatusRound instance, Status status) {
		instance.setFill(StatusRound.determineRadial(status));
	}
}
