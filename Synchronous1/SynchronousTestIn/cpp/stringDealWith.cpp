
#include <stdio.h>
#include <iostream>
#include <string.h>
#include <string>
#include <stdlib.h>
#include <math.h>

using namespace std;

const int MAX = 100 + 10;
char str[MAX];

int handle(char *str , int len)
{

    for(int i = 0;i < len ;i ++)
        if(str[i] != ' ')
            return i;
    return len - 1;
}

int main()
{
    gets(str);
    //getchar();

    //C ����ע�ͷ��Ϳ��д���
    int len = strlen(str);
    int sign = handle(str , len);
    cout << "sign*************** " << sign << "\nlen**********" << len << endl;
    if(sign == len - 1)
        cout << "�޷�����\n";
    else{
            if(str[sign] == str[sign + 1] == '/')
                printf("����ע��\n");
            else if(str[sign] == '/' && str[sign + 1] == '*')
                printf("����ע��\n");
        }
    cout << endl;

    return 0;

}
