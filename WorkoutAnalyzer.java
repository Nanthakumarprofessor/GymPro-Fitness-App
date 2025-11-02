package com.gympro;

public class WorkoutAnalyzer {
    private static final double OPTIMAL_SQUAT_DEPTH = -0.8;
    private static final double MAX_KNEE_BEND = 45.0;
    
    public SquatAnalysis analyzeSquatForm(float[] accelData, float[] gyroData) {
        double depth = calculateSquatDepth(accelData);
        double kneeAlignment = calculateKneeAlignment(gyroData);
        double backAngle = calculateBackAngle(accelData);
        
        int formScore = calculateFormScore(depth, kneeAlignment, backAngle);
        String feedback = generateFeedback(depth, kneeAlignment, backAngle);
        
        return new SquatAnalysis(depth, kneeAlignment, backAngle, formScore, feedback);
    }
    
    private double calculateSquatDepth(float[] accelData) {
        // Calculate depth from vertical acceleration (Y-axis)
        double verticalAccel = accelData[1];
        return Math.abs(verticalAccel) * 0.15; // Calibrated conversion factor
    }
    
    private double calculateKneeAlignment(float[] gyroData) {
        // Analyze knee valgus/varus from rotational data
        double rotation = Math.toDegrees(gyroData[2]);
        return Math.abs(rotation);
    }
    
    private int calculateFormScore(double depth, double kneeAlignment, double backAngle) {
        int score = 100;
        
        // Depth scoring (40% weight)
        if (depth < 0.6) score -= 20;
        else if (depth < 0.8) score -= 10;
        
        // Knee alignment scoring (30% weight)
        if (kneeAlignment > 20) score -= 15;
        else if (kneeAlignment > 15) score -= 10;
        
        // Back angle scoring (30% weight)
        if (backAngle > 50) score -= 15;
        else if (backAngle > 40) score -= 10;
        
        return Math.max(score, 0);
    }
    
    private String generateFeedback(double depth, double kneeAlignment, double backAngle) {
        if (depth < 0.6) return "Go deeper - aim for thighs parallel to ground";
        if (kneeAlignment > 15) return "Keep knees aligned with toes";
        if (backAngle > 45) return "Engage core and maintain straight back";
        return "Perfect form! Keep it up";
    }
}
