class Person:
    def __init__(self, name, age):
        self.name = name
        self.age = age

    def increase_age(self):
        self.age += 1

first_person = Person("Ivana", 29)
second_person = Person("Marko", 38)

class PersonDetail(Person):
    def __init__(self, name, age, address):
        super().__init__(name, age)
        self.address = address



first_person_detail = PersonDetail("Ana", 25, "Unska 3")

first_person_detail.increase_age()

