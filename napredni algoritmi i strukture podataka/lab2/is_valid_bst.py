from typing import Optional

class Node:
    def __init__(self, value: int) -> None:
        self.value = value
        self.left = self.right = None

def is_valid_bst(node: Optional[Node], min_value: Optional[int] = None, max_value: Optional[int] = None) -> bool:
    # Prazno stablo je valjani BST
    if node is None:
        return True

    # Provjera uvjeta BST-a
    if (min_value is not None and node.value <= min_value) or (max_value is not None and node.value >= max_value):
        return False

    # Provjera valjanosti za lijevi i desni podčvor
    return (is_valid_bst(node.left, min_value, node.value) and
            is_valid_bst(node.right, node.value, max_value))

# Primjer stabla
root = Node(10)
root.left = Node(5)
root.right = Node(15)
root.left.left = Node(3)
root.left.right = Node(7)
root.right.left = Node(12)
root.right.right = Node(20)

# Provjera valjanosti BST-a
if is_valid_bst(root):
    print("Stablo je valjani BST.")
else:
    print("Stablo nije valjani BST.")
