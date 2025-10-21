person_data = {
    "Hrvoje": 1995,
    "Jelena": 1993,
    "Marijana": 1997,
    "Mateja": 2002
}

for person in person_data:
    person_data[person] -= 1

year_age = []

for year in person_data.values():
    age = 2024-year
    year_age.append((year,age))


    