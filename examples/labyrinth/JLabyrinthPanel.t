/*
 * Copyright (c) 2004-2015, Universite de Lorraine, Inria
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *  - Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  - Neither the name of the Inria nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package labyrinth;

import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Graphics;

import java.util.HashMap;
import java.util.Iterator;

import labyrinth.boulder.types.*;

public class JLabyrinthPanel extends JPanel {
	private HashMap space, lastDrawnSpace;

	private Image[] img;

  	%include { boulder/boulder.tom } 

	JLabyrinthPanel() {
		space = new HashMap();
		lastDrawnSpace = null;
	}

	public void setBDSpace(HashMap space) {
		this.space = space;
	}

	public void setImages(Image[] images) {
		this.img = images;
	}

	public void update(Graphics g) {
		System.out.println("update called");
		/*Image im = img[0];
		 for(int i=0;i<BoulderDashImagesData.NUM_IMAGES-1;i++) {
		 img[i] = img[i+1];
		 }
		 img[BoulderDashImagesData.NUM_IMAGES-1] = im;
		 paint(g);
		 */
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.fillRect(0,0,400,400);
		//Graphics2D g2d = (Graphics2D)g;
		if(lastDrawnSpace != null && space.isEmpty()) {
			space = lastDrawnSpace;
		}

		Iterator it = space.values().iterator();
		// Warning between North and south,, east and west
		while(it.hasNext()) {
			Bead b = (Bead) it.next();
			int x = b.getpos().getx();
			int y = b.getpos().gety();
			int index = 0;
			%match(b){
				bead[value=Wall()] -> { index = 2; } 
				bead[value=Empty()] -> { index = 0; } 
				bead[value=Goal()] -> { index = 7; } 
				bead[value=Player()] -> { index = 5; }  
				bead[value=PlayerHappy()] -> { index = 14; }  
			}  		
			g.drawImage(img[index], x*LabyrinthImagesData.IMAGE_SIZE, y*LabyrinthImagesData.IMAGE_SIZE, null);
		}
		lastDrawnSpace = space;
	}
} // Class JBoulderDashPanel
