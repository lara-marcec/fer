from typing import Optional

class Node:
    def __init__(self, value: int) -> None:
        self.value = value
        self.parent = self.right = self.left = None
    
    def set_left_child(self, node: Optional['Node']) -> None:

        self.left = node
        if node is not None:
            node.parent = self
         
    def set_right_child(self, node: Optional['Node']) -> None:

        self.right = node
        if node is not None:
            node.parent = self

def right_rotation(node: Node, root: Node) -> Node:
    rotator = node.left
    new_root = root
    if rotator is None:
        return new_root
    parent = node.parent
    if parent is None:
       new_root = rotator
    else:
        if parent.left == node:
            parent.set_left_child(rotator)
        else:
            parent.set_right_child(rotator)
    
    node.set_left_child(rotator.right)
    rotator.set_right_child(node)
    
    return new_root    
         
def print_tree(node: Optional[Node], level: int = 0) -> None:

    if node is None:
        return
    print_tree(node.right, level + 4)
    print(' ' * level + f'-> {node.value}')
    print_tree(node.left, level + 4)



root = Node(17)
root.left = Node(13)
root.left.left = Node(10)
root.left.right = Node(12)
root.left.right = Node(15)
root.left.right.left = Node(14)
root.right = Node(20)
root.right.left = Node(19)
root.right.right = Node(22)
root.right.left.left = Node(18)

print('Prije rotacije:')
print_tree(root)

root = right_rotation(root, root)

assert root.value == 13
assert root.left.value == 10
assert root.right.value == 17
assert root.right.left.value == 15
assert root.right.left.left.value == 14
assert root.right.right.value == 20
assert root.right.right.left.value == 19
assert root.right.right.left.left.value == 18
assert root.right.right.right.value == 22

print()
print('Nakon rotacije:')
print_tree(root)
