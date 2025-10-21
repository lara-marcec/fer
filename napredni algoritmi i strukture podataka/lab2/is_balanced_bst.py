from typing import Optional, Tuple

class Node:
    def __init__(self, value: int) -> None:
        self.value = value
        self.left = self.right = None

def check_balance(node: Optional[Node]) -> Tuple[bool, int]:
    """
    Provjerava je li stablo uravnoteženo i vraća bool za ravnotežu i int za visinu.

    Args:
        node (Node, optional): Trenutni čvor stabla.

    Returns:
        Tuple[bool, int]: Prvi element je True ako je uravnoteženo, False inače;
                          Drugi element je visina stabla.
    """
    if node is None:
        return True, 0

    # Provjeri ravnotežu lijevog podstabla
    left_balanced, left_height = check_balance(node.left)
    if not left_balanced:
        return False, 0

    # Provjeri ravnotežu desnog podstabla
    right_balanced, right_height = check_balance(node.right)
    if not right_balanced:
        return False, 0

    # Provjeri faktor ravnoteže
    if abs(left_height - right_height) > 1:
        return False, 0

    # Visina trenutnog čvora je 1 + max visina lijevog i desnog podstabla
    current_height = 1 + max(left_height, right_height)
    return True, current_height

def is_balanced(root: Optional[Node]) -> bool:
    balanced, _ = check_balance(root)
    return balanced

# Primjer stabla
root = Node(10)
root.left = Node(5)
root.right = Node(15)
root.left.left = Node(3)
root.left.right = Node(7)
root.right.left = Node(12)
root.right.right = Node(20)
root.right.right.right = Node(25)  # Dodavanje neuravnoteženog čvora

if is_balanced(root):
    print("Stablo je uravnoteženo.")
else:
    print("Stablo nije uravnoteženo.")
