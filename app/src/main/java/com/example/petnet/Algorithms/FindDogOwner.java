package com.example.petnet.Algorithms;

import android.util.Log;
import android.util.Pair;

import com.example.petnet.Objects.Dog;
import com.example.petnet.Objects.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;

public class FindDogOwner {

    private static final String TAG = "FindFogOwner";
    private static final String distance_type = "K";
    private static final double minimum_distance = 5;
    private static final double correct_race = 1, not_sure_race = 0.5, correct_size = 1, almost_correct_size = 0.5;
    private static int num_of_correct_colors;
    private static double color_value;

    public static HashMap<String,Double> find_dog_possible_owners(Task<QuerySnapshot> task, Dog dogToFind) {

        HashMap<String,Double> possibles_owners_HM = new HashMap<String,Double>();

        if (task.isSuccessful()) {

            double candidate_dog_x, candidate_dog_y, dog_to_find_x, dog_to_find_y;
            double percent_color, percent_race, percent_high, percent_gender, final_sum_of_owner_probability = 0;
            int num_of_colors;
            Pair<String,Double> user_probability_pair;

            for (QueryDocumentSnapshot dogDocument : task.getResult()) {
                String UID = dogDocument.getId();
                Dog dogToCheck = dogDocument.toObject(Dog.class);
                Log.d(TAG, "onComplete: uid:" + UID + "\n Dog:" + dogToCheck.toString());

                // get candidate and lost dogs locations
                candidate_dog_x = dogToCheck.getAddress().get(0);
                candidate_dog_y = dogToCheck.getAddress().get(1);
                dog_to_find_x = dogToFind.getAddress().get(0);
                dog_to_find_y = dogToFind.getAddress().get(1);

                // check if the distance between the dogs is less then 3 kilometers
                if (DistanceCalculator.distance(dog_to_find_x, dog_to_find_y, candidate_dog_x, candidate_dog_y, distance_type) <= minimum_distance) {

                    // check gender
                    if (dogToFind.getPet_gender() == dogToCheck.getPet_gender())
                        final_sum_of_owner_probability += correct_race;
                    else if (dogToCheck.getPet_gender() == -1)
                        final_sum_of_owner_probability += not_sure_race;
                    else
                        final_sum_of_owner_probability -= not_sure_race;

                    // check size
                    if (dogToFind.getSize() == dogToCheck.getSize())
                        final_sum_of_owner_probability += correct_size;
                    else if (dogToFind.getSize() == dogToCheck.getSize() + 1 || dogToFind.getSize() == dogToCheck.getSize() - 1)
                        final_sum_of_owner_probability += almost_correct_size;
                    else
                        final_sum_of_owner_probability -= almost_correct_size;

                    //check colors
                    num_of_colors = dogToFind.getColors().size();
                    color_value = 1.0/(double)num_of_colors;

                    for (int color :dogToFind.getColors()) {
                        if (dogToCheck.getColors().contains(color))
                            final_sum_of_owner_probability += color_value;
                        else
                            final_sum_of_owner_probability =- color_value;
                    }

                    // make pair of user ID with the probability to be the owner
                    if (final_sum_of_owner_probability > 0)
                        possibles_owners_HM.put(UID,final_sum_of_owner_probability);
                }
            }
        }
        return possibles_owners_HM;
    }
}
