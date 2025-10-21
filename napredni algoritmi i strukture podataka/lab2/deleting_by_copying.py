from typing import Optional

class Node:
    def __init__(self, value: int) -> None:
        self.value = value
        self.left = None
        self.right = None

class BinarySearchTree:
    def __init__(self) -> None:
        self.root = None

    def insert(self, value: int) -> None:
        if self.root is None:
            self.root = Node(value)
        else:
            self._insert(value, self.root)

    def _insert(self, value: int, node: Node) -> None:
        if value < node.value:
            if node.left is None:
                node.left = Node(value)
            else:
                self._insert(value, node.left)
        else:
            if node.right is None:
                node.right = Node(value)
            else:
                self._insert(value, node.right)

    def delete(self, value: int) -> None:
        self.root = self._delete(value, self.root)

    def _delete(self, value: int, node: Optional[Node]) -> Optional[Node]:
        if node is None:
            return None

        # Pronađi čvor koji treba obrisati
        if value < node.value:
            node.left = self._delete(value, node.left)
        elif value > node.value:
            node.right = self._delete(value, node.right)
        else:
            # Čvor s jednim djetetom ili bez djece
            if node.left is None:
                return node.right
            elif node.right is None:
                return node.left

            # Čvor s dva djeteta: pronađi prethodnika (najveći u lijevom podstablu)
            predecessor = self._find_max(node.left)
            node.value = predecessor.value  # Zamijeni vrijednosti
            node.left = self._delete(predecessor.value, node.left)  # Obriši prethodnika

        return node

    def _find_max(self, node: Node) -> Node:
        current = node
        while current.right is not None:
            current = current.right
        return current

    def print_tree(self, node: Optional[Node], level: int = 0) -> None:
        if node is not None:
            self.print_tree(node.right, level + 4)
            print(' ' * level + f'-> {node.value}')
            self.print_tree(node.left, level + 4)

# Primjer korištenja
bst = BinarySearchTree()
bst.insert(10)
bst.insert(5)
bst.insert(15)
bst.insert(3)
bst.insert(7)
bst.insert(12)
bst.insert(18)

print("Stablo prije brisanja:")
bst.print_tree(bst.root)

# Brisanje čvora
bst.delete(5)

print("\nStablo nakon brisanja čvora 5:")
bst.print_tree(bst.root)

# Brisanje čvora s dva djeteta
bst.delete(10)

print("\nStablo nakon brisanja čvora 10:")
bst.print_tree(bst.root)
