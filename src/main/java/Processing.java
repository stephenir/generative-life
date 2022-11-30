import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import processing.core.PApplet;


public class Processing extends PApplet {
    
    private static final int SIZE = 1000;
    
    private List<Particle> allParticles = new ArrayList<>();
    
    private List<Particle> yellow = create(1000, Color.yellow);
    private List<Particle> red = create(1000, Color.red);
    private List<Particle> green = create(1000, Color.green);
    
    public Processing() {
        allParticles.addAll(yellow);
        allParticles.addAll(red);
        allParticles.addAll(green);
    }
    
    public void settings() {
        size(SIZE, SIZE);
        //background(0);
        smooth();
    }
    
    public void draw() {
        
        fill(0);
        rect(0, 0, SIZE, SIZE);
    
        rule(green, green, -0.32f);
        rule(green, red, -0.17f);
        rule(green, yellow, 0.34f);
        rule(red, red, -0.1f);
        rule(red, green, -0.34f);
        rule(yellow, yellow, 0.15f);
        rule(yellow, green, -0.2f);
        
        noStroke();
        
        for (Particle particle : allParticles) {
            fill(particle.getColor().getRGB());
            ellipse(particle.getX(), particle.getY(), 5, 5);
        }
    }
    
    private void rule(List<Particle> particles1, List<Particle> particles2, float g) {
        for (int j = 0; j < particles1.size(); j++) {
            
            float fx = 0;
            float fy = 0;
    
            Particle a = particles1.get(j);
            
            for (int k = 0; k < particles2.size(); k++) {
                Particle b = particles2.get(k);
                
                float dx = a.getX() - b.getX();
                float dy = a.getY() - b.getY();
                
                float d = (float)Math.sqrt(dx*dx + dy*dy);
                if (d > 0 && d < 80) {
                    float force = g * 1/d;
                    fx += force*dx;
                    fy += force*dy;
                }
            }
    
            a.vx = (a.vx + fx) * 0.5f;
            a.vy = (a.vy + fy) * 0.5f;
            a.x += a.vx;
            a.y += a.vy;
            
            if (a.x < 0 || a.x > SIZE) a.vx *=-1;
            if (a.y < 0 || a.y > SIZE) a.vy *=-1;
        }
    }
    
    public static void main(String... args) {
        PApplet.main("Processing");
    }
    
    private List<Particle> create(int n, Color c) {
        return IntStream.range(0, n)
                .boxed()
                .map(v -> new Particle((int)(Math.random() * SIZE*0.8 + SIZE/10), (int)(Math.random() * SIZE*0.8 + SIZE/10), c))
                .collect(Collectors.toList());
    }
    
    private static class Particle {
    
        private float x;
        private float y;
        private float vx;
        private float vy;
        private Color color;
        
        public Particle(int x, int y, Color color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }
    
        public float getX() {
            return x;
        }
    
        public float getY() {
            return y;
        }
    
        public Color getColor() {
            return color;
        }
    }
}

