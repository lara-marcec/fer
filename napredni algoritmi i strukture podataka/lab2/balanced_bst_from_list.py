class Node:
    def __init__(self, value: int) -> None:
        self.value = value
        self.left = None
        self.right = None

class BalancedBST:
    def __init__(self) -> None:
        self.root = None

    def sorted_list_to_bst(self, sorted_list):
        """Konvertira sortiranu listu u uravnoteženo binarno stablo."""
        if not sorted_list:
            return None
        mid = len(sorted_list) // 2  # Pronađi srednji indeks
        node = Node(sorted_list[mid])  # Stvori čvor sa srednjim elementom
        node.left = self.sorted_list_to_bst(sorted_list[:mid])  # Lijevo podstablo
        node.right = self.sorted_list_to_bst(sorted_list[mid + 1:])  # Desno podstablo
        return node

    def print_tree(self, node: Node, level: int = 0) -> None:
        """Ispisuje stablo u terminalu."""
        if node is None:
            return
        self.print_tree(node.right, level + 4)
        print(' ' * level + f'-> {node.value}')
        self.print_tree(node.left, level + 4)

# Primjer korištenja
sorted_list = [1, 2, 3, 4, 5, 6, 7, 8, 9]

bst = BalancedBST()
bst.root = bst.sorted_list_to_bst(sorted_list)

print("Uravnoteženo binarno stablo generirano iz sortirane liste:")
bst.print_tree(bst.root)
