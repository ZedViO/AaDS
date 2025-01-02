#include <iostream>
struct MyList 
{
  struct Node
  {
    Node* next;
    int value;
  };
  Node* head;
  Node* tail;

  void Constructor()
  {
    this->head = nullptr;
    this->tail = nullptr;
  }

  void PushFront(int value)
  {
    if (this->head == nullptr) {
      auto* addNode = new Node;
      addNode->value = value;
      addNode->next = nullptr;
      this->head = addNode;
      this->tail = addNode;
    }
    else 
    {
      auto* addNode = new Node;
      addNode->value = value;
      addNode->next = this->head;
      this->head = addNode;
    }
  }

  void Reverse()
  {
    Node* nextNode = nullptr;
    Node* prevNode = nullptr;
    Node* ptr = this->head;
    while (ptr) {
      nextNode = ptr->next;
      ptr->next = prevNode;
      prevNode = ptr;
      ptr = nextNode;
    }
    this->head = prevNode;
  }

  void Print() 
  {
    auto* currPtr = this->head;
    while (currPtr != nullptr) 
    {
      std::cout << currPtr->value << " ";
      currPtr = currPtr->next;
    }
    std::cout << std::endl;
  }
  
  int Length() 
  {
    auto* currPtr = this->head;
    int len = 0;
    while (currPtr != nullptr)
    {
      ++len;
      currPtr = currPtr->next;
    }
    return len;
  }
  /**
  * Makes a simple cycle by cycling the last element of a list with __index__ element
  */
  void Cycle(int index) 
  {
    if (index < this->Length()) 
    {
      auto* currPtr = this->head;
      for (int currIndex = 0; currIndex != index; ++currIndex) {
        currPtr = currPtr->next;
      }
      this->tail->next = currPtr;
      std::cout << "Cycling done!" << std::endl;
    }
    else 
    {
      std::cout << "OoA Exception!" << std::endl;
    }
  }

  void CycleDetector() {
    auto* turt = this->head;
    auto* hare = this->head;

    while (turt != nullptr && hare != nullptr) 
    {
      turt = turt->next;
      hare = hare->next;
      hare = hare->next;
      if (turt == hare) 
      {
        std::cout << "Found a cycle!" << std::endl;
        return;
      }
    }
    std::cout << "No cycle!" << std::endl;
    return;
  }
};