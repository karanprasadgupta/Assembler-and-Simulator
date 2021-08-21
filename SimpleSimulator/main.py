import numpy as np
import matplotlib.pyplot as plt

def mov_immidiate(address,val):
    register_dict[address]="00000000"+val
    register_dict['111']='0000000000000000'
    a = bin(pc).replace("0b", "")
    a = "0" * (8 - len(a)) + a
    print(a, register_dict['000'], register_dict['001'], register_dict['010'], register_dict['011'],
          register_dict['100'], register_dict['101'], register_dict['110'], register_dict['111'])

def mov_register(address1,address2):
    register_dict[address1]=register_dict[address2]
    register_dict['111'] = '0000000000000000'
    a = bin(pc).replace("0b", "")
    a = "0" * (8 - len(a)) + a
    print(a, register_dict['000'], register_dict['001'], register_dict['010'], register_dict['011'],
          register_dict['100'], register_dict['101'], register_dict['110'], register_dict['111'])

def addition(R1, R2, R3):
    register_dict['111'] = "0000000000000000"
    R1_val = register_dict[R1]
    R2_val = register_dict[R2]
    R3_val = register_dict[R3]
    R1_val=int(R1_val,2)
    R2_val = int(R2_val,2)
    R3_val = int(R3_val,2)
    R1_val=R2_val+R3_val
    R1_val = bin(R1_val).replace("0b", "")
    if len(R1_val)>16:
        R1_val=R1_val[len(R1_val)-16:len(R1_val)]
        register_dict['111']="0000000000001000"
    R1_val="0"*(16-len(R1_val))+R1_val
    register_dict[R1]=R1_val
    a = bin(pc).replace("0b", "")
    a = "0" * (8 - len(a)) + a
    print(a, register_dict['000'], register_dict['001'], register_dict['010'], register_dict['011'],
          register_dict['100'], register_dict['101'], register_dict['110'], register_dict['111'])

def left_shift(address,val):
    register_dict['111'] = "0000000000000000"
    x=int(val,2)
    value=register_dict[address]
    a=value+x*"0"
    register_dict[address]=a[len(a)-16:len(a)]
    a = bin(pc).replace("0b", "")
    a = "0" * (8 - len(a)) + a
    print(a, register_dict['000'], register_dict['001'], register_dict['010'], register_dict['011'],
          register_dict['100'], register_dict['101'], register_dict['110'], register_dict['111'])

def right_shift(address,val):
    register_dict['111'] = "0000000000000000"
    x=int(val,2)
    value=register_dict[address]
    a=x*"0"+value
    register_dict[address]=a[0:16]
    a = bin(pc).replace("0b", "")
    a = "0" * (8 - len(a)) + a
    print(a, register_dict['000'], register_dict['001'], register_dict['010'], register_dict['011'],
          register_dict['100'], register_dict['101'], register_dict['110'], register_dict['111'])

def subtraction(R1, R2, R3):
    register_dict['111'] = "0000000000000000"
    R1_val = register_dict[R1]
    R2_val = register_dict[R2]
    R3_val = register_dict[R3]
    R1_val=int(R1_val,2)
    R2_val = int(R2_val,2)
    R3_val = int(R3_val,2)
    R1_val=R2_val-R3_val
    if R1_val<0:
        R1_val=0
        register_dict['111']="0000000000001000"
    R1_val = bin(R1_val).replace("0b", "")
    R1_val="0"*(16-len(R1_val))+R1_val
    register_dict[R1]=R1_val
    a = bin(pc).replace("0b", "")
    a = "0" * (8 - len(a)) + a
    print(a, register_dict['000'], register_dict['001'], register_dict['010'], register_dict['011'],
          register_dict['100'], register_dict['101'], register_dict['110'], register_dict['111'])

def multiplication(R1, R2, R3):
    register_dict['111'] = "0000000000000000"
    R1_val = register_dict[R1]
    R2_val = register_dict[R2]
    R3_val = register_dict[R3]
    R1_val = int(R1_val, 2)
    R2_val = int(R2_val, 2)
    R3_val = int(R3_val, 2)
    R1_val = R2_val * R3_val
    R1_val = bin(R1_val).replace("0b", "")
    if len(R1_val) > 16:
        R1_val = R1_val[len(R1_val) - 16:len(R1_val)]
        register_dict['111'] = "0000000000001000"
    R1_val = "0" * (16 - len(R1_val)) + R1_val
    register_dict[R1] = R1_val
    a = bin(pc).replace("0b", "")
    a = "0" * (8 - len(a)) + a
    print(a, register_dict['000'], register_dict['001'], register_dict['010'], register_dict['011'],
          register_dict['100'], register_dict['101'], register_dict['110'], register_dict['111'])

def xor_function(address1,address2,address3):
    register_dict['111']='0000000000000000'
    val1=register_dict[address2]
    val2=register_dict[address3]
    val=''
    for i in range(0,16):
        if(val1[i]==val2[i]):
            val=val + '0'
        else:
            val=val + '1'
    register_dict[address1]=val
    a = bin(pc).replace("0b", "")
    a = "0" * (8 - len(a)) + a
    print(a, register_dict['000'], register_dict['001'], register_dict['010'], register_dict['011'],
          register_dict['100'], register_dict['101'], register_dict['110'], register_dict['111'])

def or_function(address1,address2,address3):
    register_dict['111']='0000000000000000'
    val1=register_dict[address2]
    val2=register_dict[address3]
    val=''
    for i in range(0,16):
        if(val1[i]=='0' and val2[i]=='0'):
            val=val + '0'
        else:
            val=val + '1'
    register_dict[address1]=val
    a = bin(pc).replace("0b", "")
    a = "0" * (8 - len(a)) + a
    print(a, register_dict['000'], register_dict['001'], register_dict['010'], register_dict['011'],
          register_dict['100'], register_dict['101'], register_dict['110'], register_dict['111'])

def and_function(address1,address2,address3):
    register_dict['111']='0000000000000000'
    val1=register_dict[address2]
    val2=register_dict[address3]
    val=''
    for i in range(0,16):
        if(val1[i]=='1' and val2[i]=='1'):
            val=val + '1'
        else:
            val=val + '0'
    register_dict[address1]=val
    a = bin(pc).replace("0b", "")
    a = "0" * (8 - len(a)) + a
    print(a, register_dict['000'], register_dict['001'], register_dict['010'], register_dict['011'],
          register_dict['100'], register_dict['101'], register_dict['110'], register_dict['111'])

def not_function(address1,address2):
    register_dict['111']='0000000000000000'
    val1=register_dict[address2]
    val=''
    for i in range(0,16):
        if(val1[i]=='1'):
            val=val + '0'
        else:
            val=val + '1'
    register_dict[address1]=val
    a = bin(pc).replace("0b", "")
    a = "0" * (8 - len(a)) + a
    print(a, register_dict['000'], register_dict['001'], register_dict['010'], register_dict['011'],
          register_dict['100'], register_dict['101'], register_dict['110'], register_dict['111'])

def divide(R3,R4):
    register_dict['111'] = "0000000000000000"
    R3_val = register_dict[R3]
    R3_val = int(R3_val, 2)
    R4_val = register_dict[R4]
    R4_val = int(R4_val, 2)
    R0_val=R3_val//R4_val
    R1_val=R3_val%R4_val
    R0_val = bin(R0_val).replace("0b", "")
    R0_val = "0" * (16 - len(R0_val)) + R0_val
    R1_val = bin(R1_val).replace("0b", "")
    R1_val = "0" * (16 - len(R1_val)) + R1_val
    register_dict["000"]=R0_val
    register_dict["001"]=R1_val
    a = bin(pc).replace("0b", "")
    a = "0" * (8 - len(a)) + a
    print(a, register_dict['000'], register_dict['001'], register_dict['010'], register_dict['011'],
          register_dict['100'], register_dict['101'], register_dict['110'], register_dict['111'])

def uncond_jmp(mem_addr,pc):
    register_dict['111'] = '0000000000000000'
    addr = int(mem_addr,2)
    a = bin(pc).replace("0b", "")
    a = "0" * (8 - len(a)) + a
    print(a, register_dict['000'], register_dict['001'], register_dict['010'], register_dict['011'],
          register_dict['100'], register_dict['101'], register_dict['110'], register_dict['111'])
    pc = addr
    return pc

def jlt(mem_addr,pc):
    if(register_dict['111'])[-3]=='1':
        register_dict['111'] = '0000000000000000'
        addr = int(mem_addr, 2)
        a = bin(pc).replace("0b", "")
        a = "0" * (8 - len(a)) + a
        print(a, register_dict['000'], register_dict['001'], register_dict['010'], register_dict['011'],
              register_dict['100'], register_dict['101'], register_dict['110'], register_dict['111'])
        pc = addr
        return pc
    else:
        register_dict['111'] = '0000000000000000'
        a = bin(pc).replace("0b", "")
        a = "0" * (8 - len(a)) + a
        print(a, register_dict['000'], register_dict['001'], register_dict['010'], register_dict['011'],
              register_dict['100'], register_dict['101'], register_dict['110'], register_dict['111'])
        pc+=1
        return pc

def jgt(mem_addr,pc):
    if (register_dict['111'])[-2] == '1':
        register_dict['111'] = '0000000000000000'
        addr = int(mem_addr, 2)
        a = bin(pc).replace("0b", "")
        a = "0" * (8 - len(a)) + a
        print(a, register_dict['000'], register_dict['001'], register_dict['010'], register_dict['011'],
              register_dict['100'], register_dict['101'], register_dict['110'], register_dict['111'])
        pc = addr
        return pc
    else:
        register_dict['111'] = '0000000000000000'
        a = bin(pc).replace("0b", "")
        a = "0" * (8 - len(a)) + a
        print(a, register_dict['000'], register_dict['001'], register_dict['010'], register_dict['011'],
              register_dict['100'], register_dict['101'], register_dict['110'], register_dict['111'])
        pc+=1
        return pc

def je(mem_addr,pc):
    if (register_dict['111'])[-1] == '1':
        register_dict['111'] = '0000000000000000'
        addr = int(mem_addr, 2)
        a = bin(pc).replace("0b", "")
        a = "0" * (8 - len(a)) + a
        print(a, register_dict['000'], register_dict['001'], register_dict['010'], register_dict['011'],
              register_dict['100'], register_dict['101'], register_dict['110'], register_dict['111'])
        pc = addr
        return pc
    else:
        register_dict['111'] = '0000000000000000'
        a = bin(pc).replace("0b", "")
        a = "0" * (8 - len(a)) + a
        print(a, register_dict['000'], register_dict['001'], register_dict['010'], register_dict['011'],
              register_dict['100'], register_dict['101'], register_dict['110'], register_dict['111'])
        pc+=1
        return pc

def compare(R1,R2):
    register_dict['111'] = "0000000000000000"
    R1_val = register_dict[R1]
    R2_val = register_dict[R2]
    R1_val = int(R1_val, 2)
    R2_val = int(R2_val, 2)
    if R1_val==R2_val:
        register_dict['111'] = "0000000000000001"
    elif R1_val>R2_val:
        register_dict['111'] = "0000000000000010"
    else:
        register_dict['111'] = "0000000000000100"
    a=bin(pc).replace("0b", "")
    a="0"*(8-len(a))+a
    print(a, register_dict['000'], register_dict['001'], register_dict['010'], register_dict['011'],
          register_dict['100'], register_dict['101'], register_dict['110'], register_dict['111'])

def load(reg,mem_addr):
    register_dict['111'] = "0000000000000000"
    m_val = int(mem_addr,2)
    register_dict[reg]= memory[m_val]
    a = bin(pc).replace("0b", "")
    a = "0" * (8 - len(a)) + a
    print(a, register_dict['000'], register_dict['001'], register_dict['010'], register_dict['011'],
          register_dict['100'], register_dict['101'], register_dict['110'], register_dict['111'])

def store(reg,mem_addr):
    register_dict['111'] = "0000000000000000"
    m_val = int(mem_addr, 2)
    memory[m_val] = register_dict[reg]
    a = bin(pc).replace("0b", "")
    a = "0" * (8 - len(a)) + a
    print(a, register_dict['000'], register_dict['001'], register_dict['010'], register_dict['011'],
          register_dict['100'], register_dict['101'], register_dict['110'], register_dict['111'])

def hlt():
    register_dict['111'] = "0000000000000000"
    a = bin(pc).replace("0b", "")
    a = "0" * (8 - len(a)) + a
    print(a, register_dict['000'], register_dict['001'], register_dict['010'], register_dict['011'],
          register_dict['100'], register_dict['101'], register_dict['110'], register_dict['111'])

def plot_mat():
    x1 = np.array(cc_array)
    y1 = np.array(pc_arr)
    x2 = np.array(cc_ls_arr)
    y2 = np.array(ls_arr)
    plt.scatter(x1, y1,color='blue')
    plt.scatter(x2, y2,color='blue')
    plt.title("Memory Access Plot")
    plt.ylabel("Memory Address")
    plt.xlabel("Cycle Number")
    plt.show()

if __name__ == '__main__':
    user_input = []
    while True:
        curr_line = input()
        user_input.append(curr_line)
        if curr_line == '1001100000000000':
            break
    register_dict = {'000':'0000000000000000','001':'0000000000000000','010':'0000000000000000','011':'0000000000000000',
                     '100':'0000000000000000','101':'0000000000000000','110':'0000000000000000','111':'0000000000000000'}
    memory = []
    pc = 0
    for i in range(256):
        memory.append('0000000000000000')
    cc=0
    cc_array=[]
    pc_arr=[]
    cc_ls_arr=[]
    ls_arr=[]
while(True):
    cc_array.append(cc)
    pc_arr.append(pc)
    curr=user_input[pc]
    if curr[0:5]=='00000':
         addition(curr[7:10],curr[10:13],curr[13:])
         pc+=1
    elif curr[0:5]=='00001':
         subtraction(curr[7:10],curr[10:13],curr[13:])
         pc+=1
    elif curr[0:5]=='00010':
         mov_immidiate(curr[5:8],curr[8:16])
         pc+=1
    elif curr[0:5]=='00011':
         mov_register(curr[10:13],curr[13:16])
         pc+=1
    elif curr[0:5]=='00100':
         load(curr[5:8],curr[8:16])
         cc_ls_arr.append(cc)
         ls_arr.append(int(curr[8:16],2))
         pc+=1
    elif curr[0:5]=='00101':
         store(curr[5:8],curr[8:16])
         cc_ls_arr.append(cc)
         ls_arr.append(int(curr[8:16], 2))
         pc+=1
    elif curr[0:5]=='00110':
         multiplication(curr[7:10],curr[10:13],curr[13:])
         pc+=1
    elif curr[0:5]=='00111':
         divide(curr[10:13],curr[13:16])
         pc+=1
    elif curr[0:5]=='01000':
         right_shift(curr[5:8],curr[8:16])
         pc+=1
    elif curr[0:5]=='01001':
         left_shift(curr[5:8],curr[8:16])
         pc+=1
    elif curr[0:5]=='01010':
         xor_function(curr[7:10],curr[10:13],curr[13:])
         pc+=1
    elif curr[0:5]=='01011':
         or_function(curr[7:10],curr[10:13],curr[13:])
         pc+=1
    elif curr[0:5]=='01100':
         and_function(curr[7:10],curr[10:13],curr[13:])
         pc+=1
    elif curr[0:5]=='01101':
         not_function(curr[10:13],curr[13:16])
         pc+=1
    elif curr[0:5]=='01110':
         compare(curr[10:13],curr[13:16])
         pc+=1
    elif curr[0:5]=='01111':
         pc=uncond_jmp(curr[8:16],pc)
    elif curr[0:5]=='10000':
         pc=jlt(curr[8:16],pc)
    elif curr[0:5]=='10001':
         pc=jgt(curr[8:16],pc)
    elif curr[0:5]=='10010':
         pc=je(curr[8:16],pc)
    else:
         hlt()
         break
    cc += 1
for mem in user_input:
    print(mem)
for i in range(len(user_input),256):
    print(memory[i])

plot_mat()
#print(cc)



