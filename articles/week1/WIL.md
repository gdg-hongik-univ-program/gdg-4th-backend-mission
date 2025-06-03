# 1주차 WIL
## git
- 데이터를 파일 시스템 스냅샷의 연속으로 취급하고 크기가 아주 작음  
- 데이터를 스냅샷의 스트림처럼 취급

위 특징이 git이 다른 VCS와 구분되는 점이다.  

- 거의 모든 명령을 로컬에서 실행  
- 모든 것을 해시로 식별 (실제로 Git은 파일을 이름으로 저장하지 않고 해당 파일의 해시로 저장)  

Git은 파일을 Committed, Modified, Staged 이렇게 세 가지 상태로 관리    

**Committed** -  데이터가 로컬 데이터베이스에 안전하게 저장됐다는 것을 의미  
**Modified** -  수정한 파일을 아직 로컬 데이터베이스에 커밋하지 않은 것을 말함  
**Staged** - 현재 수정한 파일을 곧 커밋할 것이라고 표시한 상태를 의미  

### add와 commit
**add** - commit의 전단계 (commit하기 전에 add를 나눠서 하는 이유는 버전관리의 편의성 때문)  
**commit** - git의 저장하는 단계 (commit을 해주면 commit을 한 곳으로 언제든지 다시 돌아올수있기때문에 코드의 추가, 삭제가 자유로워짐)  

### push와 pull
**push** - 현재 프로젝트의 커밋된(HEAD) 내용을 원격 저장소로 내보내는 명령어  
**pull** - 원격 저장소의 소스를 가져오고 해당 소스가 현재 내 소스보다 더 최신 버전이라고 하면 지금의 버전을 해당 소스에 맞춰 올림 (merge 명령어를 사용하는 것)  

### fork와 clone
**fork** - 호스팅 서비스 내에서 내 레포지토리에 다른 사람 프로젝트의 개인 복사본을 생성  
Git 자체에서의 명령이 아닌 GitHub, GitLab 또는 Bitbucket과 같은 호스팅 서비스에서 제공되는 기능  
**clone** - 원격 저장소에서 로컬 저장소(로컬 컴퓨터)로 복사본을 만드는 데 사용  

## git에 대해 실습해보기  
- 메인 1~8번 (git 기본, 다음 단계로)
  
 <img src=https://github.com/user-attachments/assets/b7137743-8473-4353-831a-1694fc530d5d
 width="50%" height="50%"/>
 
- 원격 4번, 6번 (push, pull)
  
 <img src=https://github.com/user-attachments/assets/9a9866a2-c621-45eb-a8cd-56422b01b957
 width="50%" height="50%"/> 
 
원격 **2.6번**에 해당하는 문제의 풀이는 다음과 같다

```
git fetch origin c6:main  
git fetch origin c3:foo  
git checkout foo  
git merge main  
```
