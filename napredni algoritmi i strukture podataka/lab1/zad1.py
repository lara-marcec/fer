names = ["Marijana", "Hrvoje","Marko", "Mateja", "Jelena", "Marko", "Hrvoje"]
def reverse_sort(names :list) -> list:
    return sorted(names, reverse = True)

names_desc = reverse_sort(names)

selected_names = names_desc[0:-1]

unique_selected_names = set(selected_names)

pass_names = []

for name in unique_selected_names:
    pass_names.append(name + "- pass")


