When adding a new food to a meal, we will have a screen or popup that presents all foods from the database to chose from.
The foods in the database must not differ by their quantities. This is not only a bad user experience, but imagine all the duplicate data!
For instance, if I have rice in the database, I don't want to TWO separate foods for a 1 cup quantity and a 2 cup quantity of rice.
I just want the rice to have a unit serving size like 1 cup that all of the food's macro-nutrients are based on.
With this approach, if the meal that the food lives in needs a different serving size (say the meal only calls for a 1/2 cup of rice), this is possible without duplicating food types.
And perhaps more importantly, we can calculate the displayed macro-nutrients appropriately at run-time based on the proportion of the actual serving size to the unit serving size.

So that is why I've made the rather confusing changes you see.