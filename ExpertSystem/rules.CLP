(deftemplate Person
    (multislot restaurantname)
)

(deftemplate Restaurant
    (slot city)
    (slot category)
    (slot cuisine)
    (slot price)
    (slot rating)
)

(defrule Food-Of-Heaven
    (Restaurant (city ?cit&Allahabad) (category ?cat&Veg|Non-Veg) (cuisine ?cui&Indian|Chinese) (price ?pri&Under150) (rating ?rat))
    (test (>= (str-compare "1" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Food-Of-Heaven,Jhalwa,1)))
)

(defrule El-Chico
    (Restaurant (city ?cit&Allahabad) (category ?cat&Veg|Non-Veg) (cuisine ?cui&Indian|Italian) (price ?pri&Above300) (rating ?rat))
    (test (>= (str-compare "3" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname El-Chico,Mg-Marg Civil Lines,3)))
)

(defrule Eat-On
    (Restaurant (city ?cit&Allahabad) (category ?cat&Non-Veg) (cuisine ?cui&Indian) (price ?pri&Under150) (rating ?rat))
    (test (>= (str-compare "3" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Eat-On,George-Town,3)))
)

(defrule Aroma
    (Restaurant (city ?cit&Allahabad) (category ?cat&Veg) (cuisine ?cui&Indian) (price ?pri&Under150) (rating ?rat))
    (test (>= (str-compare "2" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Aroma,Chowk,2)))
)

(defrule Vilayat
    (Restaurant (city ?cit&Allahabad) (category ?cat&Non-Veg) (cuisine ?cui&Chinese|Italian) (price ?pri&150-300) (rating ?rat))
    (test (>= (str-compare "1" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Vilayat,Doomanganj,1)))
)

(defrule Checkers
    (Restaurant (city ?cit&Allahabad) (category ?cat&Veg|Non-Veg) (cuisine ?cui&Italian|Chinese) (price ?pri&150-300) (rating ?rat))
    (test (>= (str-compare "2" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Checkers,Katra,2)))
)

(defrule Rock-On
    (Restaurant (city ?cit&Allahabad) (category ?cat&Veg) (cuisine ?cui&Indian) (price ?pri&Under150) (rating ?rat))
    (test (>= (str-compare "1" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Rock-On,Jhalwa,1)))
)

(defrule Al-Baiq
    (Restaurant (city ?cit&Allahabad) (category ?cat&Non-Veg) (cuisine ?cui&Indian) (price ?pri&150-300) (rating ?rat))
    (test (>= (str-compare "3" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Al-Baiq,Kareli,3)))
)

(defrule Warsi
    (Restaurant (city ?cit&Allahabad) (category ?cat&Veg|Non-Veg) (cuisine ?cui&Chinese) (price ?pri&Above300) (rating ?rat))
    (test (>= (str-compare "1" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Warsi,Sangam,1)))
)

(defrule Raj-Kitchen
    (Restaurant (city ?cit&Allahabad) (category ?cat&Veg) (cuisine ?cui&Indian|Chinese) (price ?pri&150-300) (rating ?rat))
    (test (>= (str-compare "2" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Raj-Kitchen,Chowk,2)))
)

(defrule Pind-Baluchi
    (Restaurant (city ?cit&Allahabad) (category ?cat&Veg|Non-Veg) (cuisine ?cui&Indian) (price ?pri&Above300) (rating ?rat))
    (test (>= (str-compare "3" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Pind-Baluchi,Civil Lines,3)))
)

(defrule Dawat
    (Restaurant (city ?cit&Lucknow) (category ?cat&Veg|Non-Veg) (cuisine ?cui&Chinese|Italian) (price ?pri&Above300) (rating ?rat))
    (test (>= (str-compare "2" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Dawat,George Town,2)))
)

(defrule Pubg-Chicken
    (Restaurant (city ?cit&Lucknow) (category ?cat&Non-Veg) (cuisine ?cui&Indian) (price ?pri&Under150) (rating ?rat))
    (test (>= (str-compare "1" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Pubg-Chicken,Indira Nagar,1)))
)

(defrule Kanha-Shyam
    (Restaurant (city ?cit&Allahabad) (category ?cat&Non-Veg) (cuisine ?cui&Indian|Chinese|Italian) (price ?pri&Above300) (rating ?rat))
    (test (>= (str-compare "3" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Kanha-Shyam,civil lines,3)))
)

(defrule Tandoor
    (Restaurant (city ?cit&Lucknow) (category ?cat&Veg) (cuisine ?cui&Indian) (price ?pri&150-300) (rating ?rat))
    (test (>= (str-compare "2" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Tandoor,Janki Puram,2)))
)

(defrule Dana-Pani
    (Restaurant (city ?cit&Allahabad) (category ?cat&Veg|Non-Veg) (cuisine ?cui&Indian) (price ?pri&Under150) (rating ?rat))
    (test (>= (str-compare "2" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Dana-Pani,jhalwa,2)))
)

(defrule Kohinoor
    (Restaurant (city ?cit&Lucknow) (category ?cat&Veg|Non-Veg) (cuisine ?cui&Italian) (price ?pri&Above300) (rating ?rat))
    (test (>= (str-compare "1" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Kohinoor,Janki Puram,1)))
)

(defrule Khuda-Baksh
    (Restaurant (city ?cit&Lucknow) (category ?cat&Non-Veg) (cuisine ?cui&Chinese|Italian) (price ?pri&150-300) (rating ?rat))
    (test (>= (str-compare "1" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Khuda-Baksh,Indira Nagar,1)))
)

(defrule Bercos
    (Restaurant (city ?cit&Lucknow) (category ?cat&Veg|Non-Veg) (cuisine ?cui&Italian|Chinese) (price ?pri&Above300) (rating ?rat))
    (test (>= (str-compare "2" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Bercos,Alam-Bagh,2)))
)

(defrule BBQ
    (Restaurant (city ?cit&Allahabad) (category ?cat&Non-Veg) (cuisine ?cui&Indian) (price ?pri&Above300) (rating ?rat))
    (test (>= (str-compare "3" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname BBQ,Jhalwa,3)))
)

(defrule Gola-Sizzler
    (Restaurant (city ?cit&Lucknow) (category ?cat&Veg|Non-Veg) (cuisine ?cui&Italian|Indian) (price ?pri&150-300) (rating ?rat))
    (test (>= (str-compare "2" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Gola-Sizzler,Alam-Bagh,2)))
)

(defrule Mac-D
    (Restaurant (city ?cit&Lucknow|Allahabad) (category ?cat&Non-Veg) (cuisine ?cui&Chinese) (price ?pri&Under150) (rating ?rat))
    (test (>= (str-compare "1" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Mac-D,Central Market,1)))
)

(defrule Dominos
    (Restaurant (city ?cit&Lucknow) (category ?cat&Veg|Non-Veg) (cuisine ?cui&Italian|Chinese) (price ?pri&150-300) (rating ?rat))
    (test (>= (str-compare "2" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Dominos,Indira Nagar,2)))
)

(defrule Pizza-Hut
    (Restaurant (city ?cit&Lucknow|Allahabad) (category ?cat&Non-Veg) (cuisine ?cui&Italian) (price ?pri&Above300) (rating ?rat))
    (test (>= (str-compare "3" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Pizza-Hut,Alam-Bagh,3)))
)

(defrule Sameer-House
    (Restaurant (city ?cit&Lucknow) (category ?cat&Veg) (cuisine ?cui&Italian|Indian|Chinese) (price ?pri&Under150) (rating ?rat))
    (test (>= (str-compare "3" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Sameer-House,Imam-Bada,3)))
)

(defrule Food_Court
    (Restaurant (city ?cit&Allahabad|Lucknow) (category ?cat&Veg|Non-Veg) (cuisine ?cui&Indian|Italian|Chinese) (price ?pri&Under150) (rating ?rat))
    (test (>= (str-compare "3" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Food_Court,Civil-Lines,3)))
)

(defrule Regal-Palace
    (Restaurant (city ?cit&Lucknow|Allahabad) (category ?cat&Veg|Non-Veg) (cuisine ?cui&Indian|Italian|Chinese) (price ?pri&150-300) (rating ?rat))
    (test (>= (str-compare "3" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Regal-Palace,Cantonment,3)))
)

(defrule Paradise
    (Restaurant (city ?cit&Lucknow|Allahabad) (category ?cat&Veg|Non-Veg) (cuisine ?cui&Indian|Italian|Chinese) (price ?pri&Above300) (rating ?rat))
    (test (>= (str-compare "3" (str-cat ?rat)) 0))
    =>
    (assert (Person (restaurantname Paradise,Centre-Park,3)))
)
